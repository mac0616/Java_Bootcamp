SELECT * FROM algoga.enrollments;

START TRANSACTION;

UPDATE algoga.user_coupons uc
JOIN algoga.payments p
  ON p.used_coupon_id = uc.user_coupon_id
SET
  uc.status = 'ISSUED',
  uc.used_at = NULL
WHERE p.user_id = 2
  AND p.course_id = 53
  AND p.payment_type = 'LECTURE_ONLY';

DELETE FROM algoga.mileage_histories
WHERE user_id = 2
  AND course_id = 53
  AND type = 'USE';

DELETE FROM algoga.enrollments
WHERE user_id = 2
  AND lecture_id = 53;

DELETE FROM algoga.payments
WHERE idempotency_key = 'LECTURE_53_2';

COMMIT;





START TRANSACTION;

-- 1. 결제에 사용된 쿠폰이 있으면 다시 사용 가능 상태로 복구
UPDATE algoga.user_coupons uc
JOIN algoga.payments p
  ON p.used_coupon_id = uc.user_coupon_id
SET
  uc.status = 'ISSUED',
  uc.used_at = NULL
WHERE p.user_id = 2
  AND p.course_id = 53
  AND p.payment_type = 'LECTURE_ONLY'
  AND p.status = 'SUCCESS';

-- 2. 결제에 사용된 마일리지 차감 내역이 있으면 삭제해서 잔액 복구
DELETE FROM algoga.mileage_histories
WHERE user_id = 2
  AND course_id = 53
  AND type = 'USE';

-- 만약 실제 테이블명이 mileage_history면 위 DELETE 대신 이걸 사용
-- DELETE FROM algoga.mileage_history
-- WHERE user_id = 2
--   AND course_id = 53
--   AND type = 'USE';

-- 3. 결제 상태를 환불/취소 상태로 변경
UPDATE algoga.payments
SET status = 'REFUNDED'
WHERE user_id = 2
  AND course_id = 53
  AND payment_type = 'LECTURE_ONLY'
  AND status = 'SUCCESS';

-- 4. 수강 등록 삭제
DELETE FROM algoga.enrollments
WHERE user_id = 2
  AND lecture_id = 53;

COMMIT;