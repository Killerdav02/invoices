-- companies
CREATE INDEX idx_companies_deleted ON companies(deleted_at);

-- company_users
CREATE INDEX idx_company_users_company ON company_users(company_id);
CREATE INDEX idx_company_users_auth0 ON company_users(auth0_user_id);
CREATE INDEX idx_company_users_email ON company_users(email);
CREATE INDEX idx_company_users_role ON company_users(company_id, role);
CREATE INDEX idx_company_users_status ON company_users(company_id, status);

-- suppliers
CREATE INDEX idx_proveedores_empresa ON suppliers(company_id);
CREATE INDEX idx_suppliers_created_by ON suppliers(created_by_user_id);
CREATE INDEX idx_suppliers_deleted ON suppliers(company_id, deleted_at);

-- invoices
CREATE INDEX idx_facturas_empresa_estado ON invoices(company_id, current_status);
CREATE INDEX idx_facturas_vencimiento ON invoices(company_id, due_date);
CREATE INDEX idx_facturas_proveedor ON invoices(supplier_id);
CREATE INDEX idx_facturas_actualizacion ON invoices(updated_at);
CREATE INDEX idx_facturas_created_by ON invoices(created_by_user_id);
CREATE INDEX idx_facturas_approved_by ON invoices(approved_by_user_id);
CREATE INDEX idx_facturas_deleted ON invoices(company_id, deleted_at);

-- invoice_line_items
CREATE INDEX idx_line_items_invoice ON invoice_line_items(invoice_id);

-- invoice_sources
CREATE INDEX idx_sources_invoice ON invoice_sources(invoice_id);
CREATE INDEX idx_sources_type ON invoice_sources(source_type);

-- invoice_files
CREATE INDEX idx_archivos_tipo ON invoice_files(invoice_id, file_type);
CREATE INDEX idx_archivos_uploaded_by ON invoice_files(uploaded_by_user_id);

-- invoice_validations
CREATE INDEX idx_validaciones_estado ON invoice_validations(invoice_id, status);
CREATE INDEX idx_validaciones_user ON invoice_validations(validated_by_user_id);

-- invoice_status_history
CREATE INDEX idx_historial_fecha ON invoice_status_history(invoice_id, changed_at);
CREATE INDEX idx_historial_changed_by ON invoice_status_history(changed_by_user_id);

-- invoice_rejections
CREATE INDEX idx_rejections_invoice ON invoice_rejections(invoice_id);
CREATE INDEX idx_rejections_history ON invoice_rejections(status_history_id);

-- payment_schedules
CREATE INDEX idx_pagos_fecha_estado ON payment_schedules(scheduled_date, status);
CREATE INDEX idx_pagos_factura ON payment_schedules(invoice_id);
CREATE INDEX idx_pagos_created_by ON payment_schedules(created_by_user_id);
CREATE INDEX idx_pagos_updated_by ON payment_schedules(updated_by_user_id);
CREATE INDEX idx_pagos_deleted ON payment_schedules(invoice_id, deleted_at);
