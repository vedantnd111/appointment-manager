package com.aspirants.appointment_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = { "category", "stores" })
public class VendorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    @EqualsAndHashCode.Include
    private Long vendorId;

    @NotBlank(message = "Vendor name is required")
    @Size(max = 100)
    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100)
    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Size(max = 1000)
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Size(max = 15)
    @Column(name = "gst_number", unique = true)
    private String gstNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Store> stores = new ArrayList<>();
}
