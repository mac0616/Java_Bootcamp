-- 트랜잭션의 개념 , ACID의 원칙 학습

-- 실습용 테이블 생성
create table accounts (
	id int primary key,
	owner varchar(50),
    balance decimal(10, 2)			-- 실수
);

insert into accounts (id, owner, balance)
values (1, '홍길동', 10000.00);

insert into accounts (id, owner, balance)
values (2, '김철수', 5000.00);

select * from accounts;

-- [시나리오]
/*
	홍길동 계좌에서 김철수 계좌로 3000원 이체하려고 한다.
    계좌 이체는 논리적으로 하나의 작업이지만, 실제 SQL 구문은 2단계이다.
    1. 홍길동 계좌에서 3000원 차감
    2. 김철수 계좌에서 3000원 증액
    
    트랜잭션이 중요한 이유!!!!!!!!!
    - 홍길동 계좌에서 3000원 차감 후 은행 정전...
    - 트랜젝션이 없다면, 홍길동은 3000원을 잃고 김철수는 3000원을 받지 못한다.
    이를
    * 데이터의 일관성이 지켜지지 않았다고 표현함.
    
    트랜잭션은 두 개의 update 구문을 하나의 논리적인 작업 단위로 묶어,
    두 작업이 모두 성공하거나, 또는 하나라도 실패하게 되면 원상복구 하도록 보장하는 기능이다.
    
    이 트랜잭션 기능을 통해 데이터베이스는 어떤 상황이라도 데이터의 무결성과 일관성을 지킬 수 있다.
*/

-- start transaction : 지급무터 시작되는 모든 SQL 작업을 하나로 묶는 명령어
-- commit : 트랜잭션 내의 모든 작업이 성공적으로 완료 되면 DB에 영구 보관 (저장)
-- rollback : 트랜잭션 내의 작업 중 하나라도 문제가 발생하면, 모든 변경 사항 취소

-- 정상적인 계좌 이체 시나리오
select * from accounts;

start transaction;

-- 홍길동 계좌 3000원 차감
update accounts set balance = balance - 3000 where id = 1;

-- 김철수 계좌 3000원 증액
update accounts set balance = balance + 3000 where id = 2;

-- 중간 확인
-- 아직 우리 눈에만 확인이 되며, 다른 사람에게는 반영 안 된 상태
select * from accounts;

-- 모든 작업 성공. 반영 시작
commit;

-- 최종 확인
-- DB에 영구적으로 반영된 상태
select * from accounts;

-- 문제가 발생한 계좌이체
-- 초기상태 재세팅
update accounts set balance = 10000 where id = 1;
update accounts set balance = 5000 where id = 2;

start transaction;

-- 홍길동 계좌 3000원 차감
update accounts set balance = balance - 3000 where id = 1;

-- 문제 발생 : 없는 계좌 (id=99)
update accounts set balance = balance + 3000 where id = 99;

-- 중간 상태 확인
select * from accounts;

-- rollback
rollback;

-- 최종 확인
select * from accounts;

drop table accounts;
