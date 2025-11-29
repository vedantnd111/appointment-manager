package com.aspirants.appointment_manager.entity;

import com.aspirants.appointment_manager.enums.DayOfWeek;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "vendor_availabilities")
public class VendorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long availabilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private VendorProfile vendor;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", length = 20, nullable = false)
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    // Constructors
    public VendorAvailability() {
    }

    public VendorAvailability(Long availabilityId, VendorProfile vendor, DayOfWeek dayOfWeek,
            LocalTime startTime, LocalTime endTime, Boolean isAvailable) {
        this.availabilityId = availabilityId;
        this.vendor = vendor;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public VendorProfile getVendor() {
        return vendor;
    }

    public void setVendor(VendorProfile vendor) {
        this.vendor = vendor;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        VendorAvailability that = (VendorAvailability) o;
        return Objects.equals(availabilityId, that.availabilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availabilityId);
    }

    @Override
    public String toString() {
        return "VendorAvailability{" +
                "availabilityId=" + availabilityId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
