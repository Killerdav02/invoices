# Endpoint Test Order (Swagger)

Esta guia te permite probar los endpoints en un orden que respeta dependencias.

## 0. Prerrequisitos

1. Tener la app corriendo en `http://localhost:8080`.
2. Si ves `Port 8080 was already in use`, cierra la instancia previa o cambia `server.port`.
3. Tener un Access Token de Auth0 con:
- issuer: `https://dev-amangasoft.us.auth0.com/`
- audience: `https://api.amanga.com/v1`
- claim `sub`
- claim `email` (necesario para `/api/v1/companies/register`)
4. En Swagger (`/swagger-ui/index.html`) usar **Authorize** y pegar `Bearer <token>`.

## 1. Variables que vas a reutilizar

Guarda estos IDs conforme avances:

- `companyId`
- `adminUserId`
- `employeeUserId`
- `supplierId`
- `invoiceId`
- `paymentScheduleId`

## 2. Onboarding de empresa y usuarios

### 2.1 Registrar empresa + primer admin (self-register)

**POST** `/api/v1/companies/register`

Body ejemplo:

```json
{
  "companyName": "Empresa Demo",
  "taxId": "RFC-DEMO-001",
  "countryCode": "COL",
  "currency": "COP",
  "adminName": "Admin Demo"
}
```

Esperado:
- `201 Created`
- respuesta con `companyId` y admin en estado `ACTIVE`.

### 2.2 Consultar empresa

**GET** `/api/v1/companies/{companyId}`

Esperado:
- `200 OK`
- datos de la empresa creada.

### 2.3 Listar usuarios de la empresa

**GET** `/api/v1/companies/{companyId}/users`

Esperado:
- `200 OK`
- al menos 1 usuario (el admin).

### 2.4 Invitar empleado

**POST** `/api/v1/users/invite`

Body ejemplo:

```json
{
  "email": "empleado.demo@amanga.com",
  "name": "Empleado Demo",
  "role": "EMPLOYEE"
}
```

Esperado:
- `201 Created`
- usuario en estado `INVITED`.

### 2.5 Registrar usuario directo en empresa (flujo existente)

**POST** `/api/v1/companies/{companyId}/users`

Body ejemplo:

```json
{
  "email": "admin2.demo@amanga.com",
  "password": "Admin1234!",
  "name": "Admin Secundario",
  "role": "ADMIN"
}
```

Esperado:
- `201 Created`
- usuario creado (normalmente `ACTIVE`).

### 2.6 Cambiar rol de usuario

**PATCH** `/api/v1/companies/{companyId}/users/{companyUserId}/role`

Body ejemplo:

```json
{
  "role": "ADMIN"
}
```

Esperado:
- `200 OK`
- usuario actualizado.

### 2.7 Deshabilitar usuario

**DELETE** `/api/v1/companies/{companyId}/users/{companyUserId}`

Esperado:
- `204 No Content`.

## 3. Suppliers

### 3.1 Crear supplier

**POST** `/api/v1/companies/{companyId}/suppliers`

Body ejemplo:

```json
{
  "name": "Proveedor Uno",
  "taxId": "NIT-900123456",
  "email": "proveedor1@demo.com",
  "phone": "+57-3000000000",
  "countryCode": "COL",
  "type": "PERSONA_MORAL"
}
```

Esperado:
- `201 Created`
- guardar `supplierId`.

### 3.2 Listar suppliers

**GET** `/api/v1/companies/{companyId}/suppliers`

Esperado:
- `200 OK`
- lista con el supplier creado.

### 3.3 Obtener supplier

**GET** `/api/v1/companies/{companyId}/suppliers/{supplierId}`

Esperado:
- `200 OK`.

### 3.4 Actualizar supplier

**PUT** `/api/v1/companies/{companyId}/suppliers/{supplierId}`

Body ejemplo (ajusta al schema mostrado por Swagger):

```json
{
  "name": "Proveedor Uno Actualizado",
  "taxId": "NIT-900123456",
  "email": "proveedor1+upd@demo.com",
  "phone": "+57-3111111111",
  "countryCode": "COL",
  "type": "PERSONA_MORAL"
}
```

Esperado:
- `200 OK`.

## 4. Invoices

### 4.1 Crear invoice

**POST** `/api/v1/companies/{companyId}/invoices`

Body ejemplo:

```json
{
  "supplierId": 1,
  "invoiceNumber": "FAC-2026-0001",
  "issueDate": "2026-05-08",
  "dueDate": "2026-06-08",
  "subTotal": 1000.00,
  "taxAmount": 190.00,
  "discountAmount": 0.00,
  "totalAmount": 1190.00,
  "currency": "COP",
  "paymentTerms": "30 dias",
  "notes": "Factura de prueba",
  "lineItems": [
    {
      "description": "Servicio mensual",
      "quantity": 1,
      "unitPrice": 1000.00,
      "taxRate": 0.19,
      "lineTotal": 1000.00
    }
  ],
  "source": {
    "sourceType": "MANUAL",
    "sourceReference": "Carga inicial",
    "metadata": "{}",
    "receivedAt": "2026-05-08T10:00:00"
  }
}
```

Esperado:
- `201 Created`
- guardar `invoiceId`.

### 4.2 Listar invoices

**GET** `/api/v1/companies/{companyId}/invoices`

Opcionales:
- `status`
- `supplierId`
- `page`
- `size`

Esperado:
- `200 OK`.

### 4.3 Obtener invoice

**GET** `/api/v1/companies/{companyId}/invoices/{invoiceId}`

Esperado:
- `200 OK`.

### 4.4 Aprobar invoice

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/approve`

Esperado:
- `200 OK`.

### 4.5 Rechazar invoice

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/reject`

Body:

```json
{
  "reason": "Datos fiscales incompletos"
}
```

Esperado:
- `204 No Content`.

### 4.6 Cancelar invoice

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/cancel`

Esperado:
- `200 OK`.

### 4.7 Marcar invoice como pagada

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/pay`

Esperado:
- `200 OK`.

### 4.8 Registrar source adicional

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/sources`

Body ejemplo:

```json
{
  "sourceType": "API",
  "sourceReference": "Webhook-001",
  "metadata": "{\"origin\":\"tests\"}",
  "receivedAt": "2026-05-08T11:00:00"
}
```

Esperado:
- `201 Created`.

## 5. Invoice validations, files e historial

### 5.1 Validar invoice

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/validations`

Body ejemplo:

```json
{
  "validationRule": "RFC_FORMAT",
  "status": "APROBADA",
  "notes": "RFC valido"
}
```

Esperado:
- `201 Created`.

### 5.2 Subir archivo de invoice

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/files` (multipart/form-data)

Partes:
- `file`: archivo real (PDF/XML/imagen)
- `metadata`:

```json
{
  "fileType": "PDF"
}
```

Esperado:
- `201 Created`.

### 5.3 Consultar historial de invoice

**GET** `/api/v1/companies/{companyId}/invoices/{invoiceId}/history`

Esperado:
- `200 OK`
- eventos de cambios de estado.

## 6. Payments

### 6.1 Programar pago

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/payments`

Body ejemplo:

```json
{
  "scheduledDate": "2026-06-01",
  "amount": 500.00,
  "priority": "MEDIA",
  "notes": "Primer abono"
}
```

Esperado:
- `201 Created`
- guardar `paymentScheduleId`.

### 6.2 Listar pagos programados

**GET** `/api/v1/companies/{companyId}/invoices/{invoiceId}/payments`

Esperado:
- `200 OK`.

### 6.3 Marcar pago programado como pagado

**POST** `/api/v1/companies/{companyId}/invoices/{invoiceId}/payments/{paymentScheduleId}/pay`

Esperado:
- `200 OK`.

### 6.4 Cancelar pago programado

**DELETE** `/api/v1/companies/{companyId}/invoices/{invoiceId}/payments/{paymentScheduleId}`

Esperado:
- `204 No Content`.

## 7. Endpoints totales cubiertos

1. `POST /api/v1/companies/register`
2. `POST /api/v1/companies`
3. `GET /api/v1/companies/{companyId}`
4. `POST /api/v1/users/invite`
5. `POST /api/v1/companies/{companyId}/users`
6. `GET /api/v1/companies/{companyId}/users`
7. `PATCH /api/v1/companies/{companyId}/users/{companyUserId}/role`
8. `DELETE /api/v1/companies/{companyId}/users/{companyUserId}`
9. `POST /api/v1/companies/{companyId}/suppliers`
10. `GET /api/v1/companies/{companyId}/suppliers`
11. `GET /api/v1/companies/{companyId}/suppliers/{supplierId}`
12. `PUT /api/v1/companies/{companyId}/suppliers/{supplierId}`
13. `POST /api/v1/companies/{companyId}/invoices`
14. `GET /api/v1/companies/{companyId}/invoices`
15. `GET /api/v1/companies/{companyId}/invoices/{invoiceId}`
16. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/approve`
17. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/reject`
18. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/cancel`
19. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/pay`
20. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/sources`
21. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/validations`
22. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/files`
23. `GET /api/v1/companies/{companyId}/invoices/{invoiceId}/history`
24. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/payments`
25. `GET /api/v1/companies/{companyId}/invoices/{invoiceId}/payments`
26. `POST /api/v1/companies/{companyId}/invoices/{invoiceId}/payments/{paymentScheduleId}/pay`
27. `DELETE /api/v1/companies/{companyId}/invoices/{invoiceId}/payments/{paymentScheduleId}`

## 8. Notas practicas

- Casi todos los endpoints de negocio requieren usuario `ADMIN` de la empresa.
- Si un endpoint responde `401`, normalmente el token (issuer/audience/exp) es invalido.
- Si responde `403`, el usuario no tiene rol/permisos para esa accion.
- Para evitar errores de validacion, usa codigos ISO:
  - `countryCode`: 2 o 3 letras (ej. `COL`, `MX`)
  - `currency`: 3 letras (ej. `COP`, `MXN`, `USD`)
