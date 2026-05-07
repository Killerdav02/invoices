package com.amanga.invoices.application.service.invoice;

import com.amanga.invoices.application.port.in.invoice.UploadInvoiceFileUseCase;
import com.amanga.invoices.application.port.out.CurrentUserProviderPort;
import com.amanga.invoices.application.port.out.FileStoragePort;
import com.amanga.invoices.application.port.out.InvoiceFileRepositoryPort;
import com.amanga.invoices.application.port.out.InvoiceRepositoryPort;
import com.amanga.invoices.application.security.CurrentUser;
import com.amanga.invoices.domain.enums.InvoiceFileType;
import com.amanga.invoices.domain.enums.InvoiceStatus;
import com.amanga.invoices.domain.enums.StorageProvider;
import com.amanga.invoices.domain.exception.ForbiddenActionException;
import com.amanga.invoices.domain.exception.InvoiceNotFoundException;
import com.amanga.invoices.domain.exception.UnauthorizedCompanyAccessException;
import com.amanga.invoices.domain.model.Invoice;
import com.amanga.invoices.domain.model.InvoiceFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Set;

@Service
public class UploadInvoiceFileService implements UploadInvoiceFileUseCase {

    private static final Set<InvoiceStatus> UPLOAD_ALLOWED_STATUSES = Set.of(
            InvoiceStatus.RECIBIDA,
            InvoiceStatus.EN_REVISION,
            InvoiceStatus.APROBADA,
            InvoiceStatus.RECHAZADA,
            InvoiceStatus.PROGRAMADA
    );

    private final InvoiceRepositoryPort invoiceRepository;
    private final InvoiceFileRepositoryPort invoiceFileRepository;
    private final FileStoragePort fileStorage;
    private final CurrentUserProviderPort currentUserProvider;
    private final String storageBucket;
    private final StorageProvider storageProvider;

    public UploadInvoiceFileService(InvoiceRepositoryPort invoiceRepository,
                                    InvoiceFileRepositoryPort invoiceFileRepository,
                                    FileStoragePort fileStorage,
                                    CurrentUserProviderPort currentUserProvider,
                                    @Value("${app.storage.bucket:invoices-files}") String storageBucket,
                                    @Value("${app.storage.provider:LOCAL}") StorageProvider storageProvider) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceFileRepository = invoiceFileRepository;
        this.fileStorage = fileStorage;
        this.currentUserProvider = currentUserProvider;
        this.storageBucket = storageBucket;
        this.storageProvider = storageProvider;
    }

    @Override
    public InvoiceFile uploadInvoiceFile(Long invoiceId,
                                         Long companyId,
                                         Long currentUserId,
                                         byte[] content,
                                         String fileName,
                                         String contentType,
                                         InvoiceFileType fileType) {
        // 1. Get current authenticated user
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        // 2. Validate current user belongs to the company
        if (!currentUser.belongsToCompany(companyId)) {
            throw new UnauthorizedCompanyAccessException(currentUser.getId(), companyId);
        }

        // 3. Find invoice
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        // 4. Validate invoice belongs to the company
        if (!invoice.getCompanyId().equals(companyId)) {
            throw new ForbiddenActionException(
                    "Invoice " + invoiceId + " does not belong to company " + companyId);
        }

        // 5. Validate invoice is in a state that allows file uploads
        if (!UPLOAD_ALLOWED_STATUSES.contains(invoice.getStatus())) {
            throw new ForbiddenActionException(
                    "Cannot upload files to invoice " + invoiceId + " with status " + invoice.getStatus());
        }

        // 6. Upload file to storage and get the stored path
        String storedPath = fileStorage.upload(content, fileName, contentType, storageBucket);

        // 7. Build InvoiceFile record
        InvoiceFile invoiceFile = InvoiceFile.builder()
                .invoiceId(invoiceId)
                .uploadedByUserId(currentUser.getId())
                .fileType(fileType)
                .storageProvider(storageProvider)
                .storageBucket(storageBucket)
                .filePath(storedPath)
                .fileSizeBytes((long) content.length)
                .checksum(computeChecksum(content))
                .uploadedAt(LocalDateTime.now())
                .build();

        // 8. Persist and return
        return invoiceFileRepository.save(invoiceFile);
    }

    private String computeChecksum(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(content);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
