-- 각 쿼리문의 결과를 합치는 union

-- 시나리오
-- 운영팀에서 시스템 점검 관련 전체 공지 메일을 발송해야 한다.
-- 메일 발송 대상은 active 사용자와, suspended 사용자 모두이다.
-- 탈퇴한 deleted 유저는 제외한다.

-- 1. active 유저 조회
-- 2. suspended 유저 조회
-- 3. 1, 2단계 합치기

-- 단, 합칠 때는 조회하려는 컬럼 갯수가 동일해야 함.
-- 단, select 문의 컬럼 순서와 데이터 타입이 호환 가능해야 함.

-- 수학의 합집합이며, 중복된 행을 제거하여 순수한 합집합을 만든다.
-- select 뒤의 컬럼 수가 다르면 에러남. (위에는 1개 밑에는 2개 => 에러)
-- union = 중복 허용 X / union all = 중복 허용 O
select
	email,
    status
from users
where status = 'active'
union
select
	email,
    status
from users
where status = 'suspended';

-- union all
select
	email,
    status
from users
where status = 'active'
union all
select
	email,
    status
from users
where status = 'suspended';
