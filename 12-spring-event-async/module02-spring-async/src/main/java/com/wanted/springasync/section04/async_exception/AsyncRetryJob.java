package com.wanted.springasync.section04.async_exception;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "async_retry_jobs")
public class AsyncRetryJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "async_retry_job_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 30)
    private String channel;

    @Column(nullable = false, length = 255)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AsyncRetryJobStatus status;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "max_retry_count", nullable = false)
    private int maxRetryCount;

    @Column(name = "fail_until_attempt", nullable = false)
    private int failUntilAttempt;

    @Column(name = "next_retry_at", nullable = false)
    private LocalDateTime nextRetryAt;

    @Column(name = "last_error_message", length = 255)
    private String lastErrorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected AsyncRetryJob() {
    }

    private AsyncRetryJob(Long userId, String channel, String message, int maxRetryCount, int failUntilAttempt) {
        validateRetryPolicy(maxRetryCount, failUntilAttempt);

        LocalDateTime now = LocalDateTime.now();
        this.userId = userId;
        this.channel = channel;
        this.message = message;
        this.status = AsyncRetryJobStatus.PENDING;
        this.retryCount = 0;
        this.maxRetryCount = maxRetryCount;
        this.failUntilAttempt = failUntilAttempt;
        this.nextRetryAt = now;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public static AsyncRetryJob createEmailJob(Long userId, String message, int maxRetryCount, int failUntilAttempt) {
        return new AsyncRetryJob(userId, "EMAIL", message, maxRetryCount, failUntilAttempt);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public AsyncRetryJobStatus getStatus() {
        return status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public LocalDateTime getNextRetryAt() {
        return nextRetryAt;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void markSuccess() {
        this.status = AsyncRetryJobStatus.SUCCESS;
        this.lastErrorMessage = null;
        this.updatedAt = LocalDateTime.now();
    }

    public void markRetry(String errorMessage, long delaySeconds) {
        this.retryCount += 1;
        this.lastErrorMessage = errorMessage;
        this.updatedAt = LocalDateTime.now();

        if (retryCount >= maxRetryCount) {
            this.status = AsyncRetryJobStatus.FAILED;
            this.nextRetryAt = updatedAt;
            return;
        }

        this.status = AsyncRetryJobStatus.PENDING;
        this.nextRetryAt = updatedAt.plusSeconds(delaySeconds);
    }

    public boolean shouldFailThisAttempt() {
        return retryCount < failUntilAttempt;
    }

    private static void validateRetryPolicy(int maxRetryCount, int failUntilAttempt) {
        if (maxRetryCount < 1) {
            throw new IllegalArgumentException("maxRetryCount 는 1 이상이어야 합니다.");
        }
        if (failUntilAttempt < 0) {
            throw new IllegalArgumentException("failUntilAttempt 는 0 이상이어야 합니다.");
        }
    }
}