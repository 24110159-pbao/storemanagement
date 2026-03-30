package com.example.storemanagement.service.impl;


import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.BatchDAO;
import com.example.storemanagement.dao.InvoiceDAO;
import com.example.storemanagement.dao.InvoiceDetailDAO;
import com.example.storemanagement.dao.impl.BatchDAOImpl;
import com.example.storemanagement.dao.impl.InvoiceDAOImpl;
import com.example.storemanagement.dao.impl.InvoiceDetailDAOImpl;
import com.example.storemanagement.model.dto.InvoiceInputDTO;
import com.example.storemanagement.model.dto.InvoiceItemDTO;
import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.*;
import com.example.storemanagement.model.id.InvoiceDetailId;
import com.example.storemanagement.service.InvoiceService;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceDAO dao = new InvoiceDAOImpl();
    private InvoiceDetailDAO detailDAO = new InvoiceDetailDAOImpl();
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    public List<Invoice> getAll() {
        return dao.findAll();
    }

    @Override
    public void createInvoice(Invoice invoice) {

        // 🔥 BUSINESS LOGIC QUAN TRỌNG
        BigDecimal total = BigDecimal.ZERO;

        if (invoice.getDetails() != null) {
            for (var d : invoice.getDetails()) {
                BigDecimal line = d.getUnitPrice()
                        .multiply(BigDecimal.valueOf(d.getQuantity()));
                total = total.add(line);
            }
        }

        invoice.setTotalAmount(total);

        dao.save(invoice);
    }

    @Override
    public List<Invoice> getByCustomer(Integer customerId) {
        return dao.findByCustomer(customerId);
    }


    @Override
    public void createInvoice(InvoiceInputDTO dto) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Invoice invoice = new Invoice();

            Customer customer = em.find(Customer.class, dto.getCustomerId());
            Employee employee = em.find(Employee.class, dto.getEmployeeId());
            Branch branch = em.find(Branch.class, dto.getBranchId());

            invoice.setCustomer(customer);
            invoice.setEmployee(employee);
            invoice.setBranch(branch);
            invoice.setInvoiceDate(dto.getInvoiceDate());

            List<InvoiceDetail> details = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (InvoiceItemDTO item : dto.getItems()) {

                // ================== 🔥 TRỪ KHO ==================
                List<Batch> batches = em.createQuery(
                                "SELECT b FROM Batch b WHERE b.product.productID = :pid AND b.branch.branchID = :bid ORDER BY b.importDate",
                                Batch.class)
                        .setParameter("pid", item.getProductId())
                        .setParameter("bid", dto.getBranchId())
                        .getResultList();

                int need = item.getQuantity();

                for (Batch b : batches) {

                    if (need <= 0) break;

                    int available = b.getQuantity();

                    if (available >= need) {
                        b.setQuantity(available - need);
                        need = 0;
                    } else {
                        b.setQuantity(0);
                        need -= available;
                    }
                }

                // ❌ Không đủ hàng → rollback
                if (need > 0) {
                    throw new RuntimeException("Không đủ hàng cho sản phẩm: " + item.getProductId() +
                            "\n" + "Hiện tại chỉ còn lại: " + item.getQuantity());
                }

                // =================================================

                Product product = em.find(Product.class, item.getProductId());

                InvoiceDetail detail = new InvoiceDetail();
                InvoiceDetailId id = new InvoiceDetailId();

                id.setProductID(product.getProductID());

                detail.setId(id);
                detail.setInvoice(invoice);
                detail.setProduct(product);

                detail.setQuantity(item.getQuantity());
                detail.setUnitPrice(BigDecimal.valueOf(item.getUnitPrice()));

                BigDecimal line = detail.getUnitPrice()
                        .multiply(BigDecimal.valueOf(detail.getQuantity()));

                total = total.add(line);

                details.add(detail);
            }

            invoice.setDetails(details);
            invoice.setTotalAmount(total);

            // ===== SAVE =====
            em.persist(invoice);

            // set lại invoiceID cho composite key
            for (InvoiceDetail d : details) {
                d.getId().setInvoiceID(invoice.getInvoiceID());
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkStock(Integer branchId, Integer productId, int quantity) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Lấy tổng số lượng từ batch theo chi nhánh
            Long available = em.createQuery(
                            "SELECT COALESCE(SUM(b.quantity),0) FROM Batch b " +
                                    "WHERE b.product.productID = :pid AND b.branch.branchID = :bid", Long.class)
                    .setParameter("pid", productId)
                    .setParameter("bid", branchId)
                    .getSingleResult();

            return available >= quantity;
        } finally {
            em.close();
        }
    }



}
