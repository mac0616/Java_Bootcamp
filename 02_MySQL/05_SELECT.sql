-- SELECT (조회)
-- SELECT SQL 전부다. 대부분의 작업 90% 이상이 SELECT 조회 구문이다.
-- DDL(DataBase Create) , DML(INSERT , UPDATE , DELETE) 는 데이터를
-- 저장하고 관리하기 위한 준비 작업에 가깝다.

-- SELECT 구문은 관리 혹은 저장 된 데이터를 어떤 식으로 활용할 지를 담당하는 명령어이다.
-- SELECT 구문의 핵심 2가지
-- 1. 어떤 열을 가져올 것인가? (정보의 종류)
-- 2. 어떤 행을 가져올 것인가? (정보의 범위)

-- * : 애스터리스크 라고 불리우며, 모든 열을 의미하는 와일드카드이다.
select 
	* 
from users;

-- users 테이블에서 name , email 만 조회를 해보자.
SELECT
	`name`,
    `email`
FROM users;

-- as : Alias (별칭) 부여
select
	name as '사용자 이름',
    email as '이메일 주소',
	created_at as '가입일'
from users;

-- 중복된 값 제거하고 조회하기
-- distinct : 중복된 값을 제거하고 유일한 값만 조회한다.
select
	distinct role_id
from user_roles;

-- where(조건절) : 어떤 행을 가져올 것인가를 기술하는 필터링
-- where 절은 SQL 심장.
-- 수많은 데이터 속에서 원하는 조건의 데이터만 정확하게 필터링하는
-- where를 얼마나 잘 사용하느냐에 따라 SQL의 성능과 정확성이 결정된다.

-- users 테이블에서 user 의 status 가 active 가 
-- 아닌 모든 사용자의 user_id 와 status 조회
/*
	비교 연산자
    = : 같다
    != , <> : 같지 않다
    > : 크다
    < : 작다
    >= : 크거나 같다
    <= : 작거나 같다
    LIKE : 특정  패턴을 포함한다.
    IN : 목록 안의 값 중 하나와 일치한다.
    BETWEEN : 두 값 사이의 범위에 있다.
*/
select
	user_id,
    status
from users
where status != 'active';

-- users 테이블에서 user_id가 10보다 큰 사용자 조회
select 
	* 
from users 
where user_id > 10;

-- 24년 02월 01일에 가입한 회원을 모두 조회하라
-- between a and b 사이 조건(범위 조건)
select
	* 
from users
where created_at between '2024-02-01 00:00:00' and '2024-02-01 23:59:59';

-- 목록 조건(IN)
-- user_id 가 1, 5, 10 번인 사용자 정보 조회
select
	*
from users
where user_id in (1, 5, 10);   -- not in (1, 5, 10) = 1, 5, 10 빼고 조회

-- 패턴 매칭(LIKE)
-- 이름이 '김'으로 시작하는 모든 사용자 조회
-- $ : 0개 이상의 모든 문자를 의미함(와일드 카드)
-- '김%' : 김으로 시작하는 모든 문자열 의미
-- '%철수' : 철수로 끝나는 모든 문자열 의미 (ex. 박철수, 김철수, 이철수...)
-- '%이%' : '이'를 포함하는 모든 문자열 의미
-- '_' : 정확히 1개의 문자
-- '김_수' : 김으로 시작하고, 수로 끝나는 3글자 문자열 의미
select
	*
from users
where name like '김%';

-- 이메일 주소에 'jae'라는 단어가 포함된 사용자의 이름과 이메일, status를 조회하라
select
	name, 
    email, 
    status
from users
where email like '%jae%';

-- Null 값 확인 구문
-- is null : 해당 컬럼의 값이 비어있는 레코드(행 또는 값) 조회
-- is not null : 해당 컬럼의 값이 비어있지 않는 레코드 조회

-- users 테이블에서 user_id, 이름, 마지막 로그인 일시 조회를 할건데
-- 마지막 로그인 일시가 null 인 사용자만 조회
select
	user_id,
    name,
    last_login_at
from users
where last_login_at is not null;

-- 논리 연산자 (AND, OR, NOT)
-- AND : 모든 조건이 참일 때 참을 반환
-- OR : 둘 중 하나라도 참이면 참을 반환
-- NOT : 조건의 참/거짓을 반전

-- 2024년 3월 이후에 가입을 했고, Status가 Active인 사용자의 이름과 이메일 조회
select
	name,
    email
from users
where created_at >= '2024-03-01' and status = 'active';

-- 성이 '김'이거나 활성사용자가 아닌 사용자의 이름, user_id, 이메일 조회
select
	name,
    user_id,
    email
from users
where name like '김%' or status != 'active';

-- not 은 참을 거짓으로, 거짓을 참으로 반전 시키는 연산자이다.
select
	name,
    user_id,
    email
from users
where not(name like '김%' or status != 'active');

-- order by 와 limit
-- order by
-- 조회 된 결과에 명확한 순서와 질서를 부여하는 기능이다.
-- ex) 최신 가입자 순서, 이름 가나다 순서 등 정렬 기능을 제공하여, 사용자 입장에서
-- 데이터를 쉽게 인지하도록 도와준다.

-- limit
-- 조회 결과의 갯수를 제한하는 기능이다.
-- 수백만 건의 데이터를 한 번에 조회하게 되면, 시스템 상에 큰 부하를 주게 된다.
-- limit 는 '상위 5개만 보기' 혹은 '1 페이지에 10개씩 보기' 등
-- 웹 어플리케이션의 페이징 기능을 구현하는데 필수 핵심 문법이다.

-- users 테이블에서 아이디, 이름, 가입일을 조회할건데 가장 최근에 가입한 순서대로 나열 (내림차순)
select
	user_id,
    name,
    created_at
from users 
order by created_at desc;

-- users 테이블에서 아이디, 이름, 가입일을 조회할건데 가장 나중에 가입한 순서대로 나열 (오룸차순)
select
	user_id,
    name,
    created_at
from users 
order by created_at asc;

-- 가장 최근에 가입한 사용자 5명의 정보만 조회
-- order by + limit 조합은 많이 쓰인다.
select
	*
from users
order by created_at desc
limit 5;

-- 페이징 구현
-- 사용자 목록의 두 번째 페이지 (11~20번 사용자) 를 조회한다.
select
	*
from users
order by user_id asc
limit 10, 10;

-- 최종 실습
-- 요구사항
-- LXP 플랫폼의 운영팀에서 마케팅 이메일 발송 대상을 찾고 있다.
-- 1. 2024년 4월 또는 5월에 가입한 사용자여야 한다.
-- 2. 계정의 상태가 활성화 상태여야 한다.
-- 3. 이메일 주소가 'example.com'으로 끝나야 한다.
-- 4. 위 조건에 맞는 사용자들을 가장 최근에 가입한 순서로 정렬하여,
-- 5. 두 번째 페이지에 해당하는 5명(6~10)의user_id, name, email, created_at을 조회하라.

-- SQL 에서 Where 조건절의 연산 우선순위
-- 1. NOT
-- 2. AND
-- 3. OR

select
	user_id,
    name,
    email,
    created_at
from users
where 
	(created_at between '2024-04-01' and '2024-05-31 23:59:59')
	and 
    status = 'active' 
    and 
    email like '%@example.com'
order by created_at desc
limit 5, 5;


