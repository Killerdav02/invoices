package com.amanga.invoices.application.port.out;

public interface FileStoragePort {

    // Sube un archivo y devuelve la ruta donde fue almacenado
    String upload(byte[] content, String fileName, String contentType, String bucket);

    // Descarga un archivo por su ruta de almacenamiento
    byte[] download(String filePath, String bucket);

    // Elimina un archivo del storage
    void delete(String filePath, String bucket);

    // Genera una URL temporal de acceso (presigned URL)
    String generatePresignedUrl(String filePath, String bucket, int expirationMinutes);
}
