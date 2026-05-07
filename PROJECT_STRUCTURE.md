# Estructura del Proyecto

src/
├── main/
│   ├── java/com/amanga/invoices/
│   │   │
│   │   ├── InvoicesApplication.java
│   │   │
│   │   ├── shared/
│   │   │   ├── Constants.java
│   │   │   └── DateTimeProvider.java
│   │   │
│   │   ├── config/
│   │   │   ├── BeanConfig.java
│   │   │   ├── CorsConfig.java
│   │   │   ├── JpaConfig.java
│   │   │   ├── OpenApiConfig.java
│   │   │   └── SecurityConfig.java
│   │   │
│   │   ├── domain/
│   │   │   ├── enums/
│   │   │   │   ├── CompanyUserRole.java
│   │   │   │   ├── CompanyUserStatus.java
│   │   │   │   ├── InvoiceFileType.java
│   │   │   │   ├── InvoiceSourceType.java
│   │   │   │   ├── InvoiceStatus.java
│   │   │   │   ├── InvoiceValidationStatus.java
│   │   │   │   ├── PaymentPriority.java
│   │   │   │   ├── PaymentScheduleStatus.java
│   │   │   │   ├── StorageProvider.java
│   │   │   │   └── SupplierType.java
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── CompanyNotFoundException.java
│   │   │   │   ├── CompanyUserNotFoundException.java
│   │   │   │   ├── DuplicateInvoiceException.java
│   │   │   │   ├── ForbiddenActionException.java
│   │   │   │   ├── InvalidInvoiceStatusException.java
│   │   │   │   ├── InvoiceNotFoundException.java
│   │   │   │   ├── PaymentScheduleNotFoundException.java
│   │   │   │   ├── SupplierNotFoundException.java
│   │   │   │   └── UnauthorizedCompanyAccessException.java
│   │   │   │
│   │   │   └── model/
│   │   │       ├── Company.java
│   │   │       ├── CompanyUser.java
│   │   │       ├── Invoice.java
│   │   │       ├── InvoiceFile.java
│   │   │       ├── InvoiceLineItem.java
│   │   │       ├── InvoiceRejection.java
│   │   │       ├── InvoiceSource.java
│   │   │       ├── InvoiceStatusHistory.java
│   │   │       ├── InvoiceValidation.java
│   │   │       ├── PaymentSchedule.java
│   │   │       └── Supplier.java
│   │   │
│   │   ├── application/
│   │   │   ├── security/
│   │   │   │   ├── CompanyAccessValidator.java
│   │   │   │   ├── CurrentUserContext.java
│   │   │   │   └── RoleValidator.java
│   │   │   │
│   │   │   ├── port/
│   │   │   │   ├── in/
│   │   │   │   │   ├── company/
│   │   │   │   │   │   ├── CreateCompanyUseCase.java
│   │   │   │   │   │   └── GetCompanyUseCase.java
│   │   │   │   │   │
│   │   │   │   │   ├── invoice/
│   │   │   │   │   │   ├── ApproveInvoiceUseCase.java
│   │   │   │   │   │   ├── CancelInvoiceUseCase.java
│   │   │   │   │   │   ├── CreateInvoiceUseCase.java
│   │   │   │   │   │   ├── GetInvoiceHistoryUseCase.java
│   │   │   │   │   │   ├── GetInvoiceUseCase.java
│   │   │   │   │   │   ├── ListInvoicesUseCase.java
│   │   │   │   │   │   ├── MarkInvoiceAsPaidUseCase.java
│   │   │   │   │   │   ├── RejectInvoiceUseCase.java
│   │   │   │   │   │   ├── UploadInvoiceFileUseCase.java
│   │   │   │   │   │   └── ValidateInvoiceUseCase.java
│   │   │   │   │   │
│   │   │   │   │   ├── payment/
│   │   │   │   │   │   ├── CancelPaymentScheduleUseCase.java
│   │   │   │   │   │   ├── MarkPaymentAsPaidUseCase.java
│   │   │   │   │   │   └── SchedulePaymentUseCase.java
│   │   │   │   │   │
│   │   │   │   │   ├── supplier/
│   │   │   │   │   │   ├── GetSupplierUseCase.java
│   │   │   │   │   │   ├── ListSuppliersUseCase.java
│   │   │   │   │   │   └── RegisterSupplierUseCase.java
│   │   │   │   │   │
│   │   │   │   │   └── user/
│   │   │   │   │       ├── ChangeCompanyUserRoleUseCase.java
│   │   │   │   │       ├── DisableCompanyUserUseCase.java
│   │   │   │   │       ├── GetCurrentCompanyUserUseCase.java
│   │   │   │   │       ├── ListCompanyUsersUseCase.java
│   │   │   │   │       └── RegisterCompanyUserUseCase.java
│   │   │   │   │
│   │   │   │   └── out/
│   │   │   │       ├── CompanyRepositoryPort.java
│   │   │   │       ├── CompanyUserRepositoryPort.java
│   │   │   │       ├── FileStoragePort.java
│   │   │   │       ├── InvoiceFileRepositoryPort.java
│   │   │   │       ├── InvoiceRejectionRepositoryPort.java
│   │   │   │       ├── InvoiceRepositoryPort.java
│   │   │   │       ├── InvoiceSourceRepositoryPort.java
│   │   │   │       ├── InvoiceStatusHistoryRepositoryPort.java
│   │   │   │       ├── InvoiceValidationRepositoryPort.java
│   │   │   │       ├── PaymentScheduleRepositoryPort.java
│   │   │   │       └── SupplierRepositoryPort.java
│   │   │   │
│   │   │   └── service/
│   │   │       ├── company/
│   │   │       │   ├── CreateCompanyService.java
│   │   │       │   └── GetCompanyService.java
│   │   │       │
│   │   │       ├── invoice/
│   │   │       │   ├── ApproveInvoiceService.java
│   │   │       │   ├── CancelInvoiceService.java
│   │   │       │   ├── CreateInvoiceService.java
│   │   │       │   ├── GetInvoiceHistoryService.java
│   │   │       │   ├── GetInvoiceService.java
│   │   │       │   ├── ListInvoicesService.java
│   │   │       │   ├── MarkInvoiceAsPaidService.java
│   │   │       │   ├── RejectInvoiceService.java
│   │   │       │   ├── UploadInvoiceFileService.java
│   │   │       │   └── ValidateInvoiceService.java
│   │   │       │
│   │   │       ├── payment/
│   │   │       │   ├── CancelPaymentScheduleService.java
│   │   │       │   ├── MarkPaymentAsPaidService.java
│   │   │       │   └── SchedulePaymentService.java
│   │   │       │
│   │   │       ├── supplier/
│   │   │       │   ├── GetSupplierService.java
│   │   │       │   ├── ListSuppliersService.java
│   │   │       │   └── RegisterSupplierService.java
│   │   │       │
│   │   │       └── user/
│   │   │           ├── ChangeCompanyUserRoleService.java
│   │   │           ├── DisableCompanyUserService.java
│   │   │           ├── GetCurrentCompanyUserService.java
│   │   │           ├── ListCompanyUsersService.java
│   │   │           └── RegisterCompanyUserService.java
│   │   │
│   │   └── infrastructure/
│   │       ├── exception/
│   │       │   └── GlobalExceptionHandler.java
│   │       │
│   │       ├── security/
│   │       │   ├── Auth0JwtAdapter.java
│   │       │   ├── JwtCompanyUserResolver.java
│   │       │   └── SecurityPrincipal.java
│   │       │
│   │       └── adapter/
│   │           ├── in/
│   │           │   └── web/
│   │           │       ├── CompanyController.java
│   │           │       ├── CompanyUserController.java
│   │           │       ├── InvoiceController.java
│   │           │       ├── InvoiceFileController.java
│   │           │       ├── InvoiceHistoryController.java
│   │           │       ├── InvoiceValidationController.java
│   │           │       ├── PaymentScheduleController.java
│   │           │       ├── SupplierController.java
│   │           │       │
│   │           │       ├── dto/
│   │           │       │   ├── common/
│   │           │       │   │   ├── ApiErrorResponse.java
│   │           │       │   │   └── PageResponse.java
│   │           │       │   │
│   │           │       │   ├── company/
│   │           │       │   │   ├── CompanyResponse.java
│   │           │       │   │   └── CreateCompanyRequest.java
│   │           │       │   │
│   │           │       │   ├── invoice/
│   │           │       │   │   ├── CreateInvoiceRequest.java
│   │           │       │   │   ├── InvoiceFileResponse.java
│   │           │       │   │   ├── InvoiceLineItemRequest.java
│   │           │       │   │   ├── InvoiceResponse.java
│   │           │       │   │   ├── InvoiceSourceRequest.java
│   │           │       │   │   ├── InvoiceStatusHistoryResponse.java
│   │           │       │   │   ├── InvoiceValidationResponse.java
│   │           │       │   │   ├── RegisterInvoiceSourceRequest.java
│   │           │       │   │   ├── RejectInvoiceRequest.java
│   │           │       │   │   ├── UploadInvoiceFileRequest.java
│   │           │       │   │   └── ValidateInvoiceRequest.java
│   │           │       │   │
│   │           │       │   ├── payment/
│   │           │       │   │   ├── PaymentScheduleResponse.java
│   │           │       │   │   └── SchedulePaymentRequest.java
│   │           │       │   │
│   │           │       │   ├── supplier/
│   │           │       │   │   ├── CreateSupplierRequest.java
│   │           │       │   │   └── SupplierResponse.java
│   │           │       │   │
│   │           │       │   └── user/
│   │           │       │       ├── ChangeCompanyUserRoleRequest.java
│   │           │       │       ├── CompanyUserResponse.java
│   │           │       │       ├── CurrentUserResponse.java
│   │           │       │       └── RegisterCompanyUserRequest.java
│   │           │       │
│   │           │       └── mapper/
│   │           │           ├── CompanyUserWebMapper.java
│   │           │           ├── CompanyWebMapper.java
│   │           │           ├── InvoiceWebMapper.java
│   │           │           ├── PaymentScheduleWebMapper.java
│   │           │           └── SupplierWebMapper.java
│   │           │
│   │           └── out/
│   │               ├── persistence/
│   │               │   ├── adapter/
│   │               │   │   ├── CompanyPersistenceAdapter.java
│   │               │   │   ├── CompanyUserPersistenceAdapter.java
│   │               │   │   ├── InvoiceFilePersistenceAdapter.java
│   │               │   │   ├── InvoicePersistenceAdapter.java
│   │               │   │   ├── InvoiceRejectionPersistenceAdapter.java
│   │               │   │   ├── InvoiceSourcePersistenceAdapter.java
│   │               │   │   ├── InvoiceStatusHistoryPersistenceAdapter.java
│   │               │   │   ├── InvoiceValidationPersistenceAdapter.java
│   │               │   │   ├── PaymentSchedulePersistenceAdapter.java
│   │               │   │   └── SupplierPersistenceAdapter.java
│   │               │   │
│   │               │   ├── entity/
│   │               │   │   ├── CompanyJpaEntity.java
│   │               │   │   ├── CompanyUserJpaEntity.java
│   │               │   │   ├── InvoiceFileJpaEntity.java
│   │               │   │   ├── InvoiceJpaEntity.java
│   │               │   │   ├── InvoiceLineItemJpaEntity.java
│   │               │   │   ├── InvoiceRejectionJpaEntity.java
│   │               │   │   ├── InvoiceSourceJpaEntity.java
│   │               │   │   ├── InvoiceStatusHistoryJpaEntity.java
│   │               │   │   ├── InvoiceValidationJpaEntity.java
│   │               │   │   ├── PaymentScheduleJpaEntity.java
│   │               │   │   └── SupplierJpaEntity.java
│   │               │   │
│   │               │   ├── mapper/
│   │               │   │   ├── CompanyPersistenceMapper.java
│   │               │   │   ├── CompanyUserPersistenceMapper.java
│   │               │   │   ├── InvoiceFilePersistenceMapper.java
│   │               │   │   ├── InvoiceLineItemPersistenceMapper.java
│   │               │   │   ├── InvoicePersistenceMapper.java
│   │               │   │   ├── InvoiceRejectionPersistenceMapper.java
│   │               │   │   ├── InvoiceSourcePersistenceMapper.java
│   │               │   │   ├── InvoiceStatusHistoryPersistenceMapper.java
│   │               │   │   ├── InvoiceValidationPersistenceMapper.java
│   │               │   │   ├── PaymentSchedulePersistenceMapper.java
│   │               │   │   └── SupplierPersistenceMapper.java
│   │               │   │
│   │               │   └── repository/
│   │               │       ├── SpringDataCompanyRepository.java
│   │               │       ├── SpringDataCompanyUserRepository.java
│   │               │       ├── SpringDataInvoiceFileRepository.java
│   │               │       ├── SpringDataInvoiceRejectionRepository.java
│   │               │       ├── SpringDataInvoiceRepository.java
│   │               │       ├── SpringDataInvoiceSourceRepository.java
│   │               │       ├── SpringDataInvoiceStatusHistoryRepository.java
│   │               │       ├── SpringDataInvoiceValidationRepository.java
│   │               │       ├── SpringDataPaymentScheduleRepository.java
│   │               │       └── SpringDataSupplierRepository.java
│   │               │
│   │               └── storage/
│   │                   ├── LocalFileStorageAdapter.java
│   │                   └── S3FileStorageAdapter.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/com/amanga/invoices/
        └── InvoicesApplicationTests.java
