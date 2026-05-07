package com.amanga.invoices.infrastructure.adapter.out.persistence.entity;

import com.amanga.invoices.domain.enums.CompanyUserRole;
import com.amanga.invoices.domain.enums.CompanyUserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(
	name = "company_users",
	uniqueConstraints = {
		@UniqueConstraint(name = "uq_company_user_auth0", columnNames = { "company_id", "auth0_user_id" }),
		@UniqueConstraint(name = "uq_company_user_email", columnNames = { "company_id", "email" })
	}
)
public class CompanyUserJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyJpaEntity company;

	@Column(name = "auth0_user_id", nullable = false, length = 128)
	private String auth0UserId;

	@Column(name = "email", nullable = false, length = 255)
	private String email;

	@Column(name = "name", length = 255)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private CompanyUserRole role = CompanyUserRole.EMPLOYEE;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private CompanyUserStatus status = CompanyUserStatus.ACTIVE;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public CompanyUserJpaEntity() {
	}

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CompanyJpaEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyJpaEntity company) {
		this.company = company;
	}

	public String getAuth0UserId() {
		return auth0UserId;
	}

	public void setAuth0UserId(String auth0UserId) {
		this.auth0UserId = auth0UserId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CompanyUserRole getRole() {
		return role;
	}

	public void setRole(CompanyUserRole role) {
		this.role = role;
	}

	public CompanyUserStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyUserStatus status) {
		this.status = status;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(LocalDateTime lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
}
