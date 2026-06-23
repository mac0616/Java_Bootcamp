-- 데이터의 그룹을 만드는 Group BY 및 집계 학습

use wanted_lms;

-- 홍길동의 이메일은 무엇인가?
-- 'SQL 입문' 과정의 과정 설명은 무엇인가?
-- 위 예시처럼 단순한 SELECT 를 진행했었다.

-- 하지만 비즈니스의 의사결정에는 더 큰 그림이 필요하다.
-- ex) 우리 플랫폼의 총 사용자 수는 몇 명인가?
-- 	   가장 게시글을 많이 쓴 사람은 누구인가?

-- 집계 함수 -> 데이터를 하나의 값으로 요약하기

/*
	집계함수란?
    여러 행의 데이터를 입력 받아 단 하나의 결과 값을 반환하는 함수이다.
    
    count(컬럼명) : 해당 컬럼의 데이터 개수를 센다. (null 제외)
    sum(컬럼명) : 해당 컬럼의 숫자 데이터의 합계 계산
    avg(컬럼명) : 해당 컬럼의  숫자 데이터의 평균 계산
    max(컬럼명) : 해당 컬럼의 데이터 중 최댓값 찾는다.
    min(컬럼명) : 해당 컬럼의 데이터 중 최솟값 찾는다.
*/

-- 우리 LMS 플랫폼의 전체 사용자 수 조회
select 
	count(*) as '총 사용자 수'
from users;

-- user_profiles 테이블에서 bio(자기소개) 를 작성한 사용자는 몇 명인지 조회
select
	count(bio) as '자소서 작성자 수'
from user_profiles;

-- courses 테이블에 과정을 등록한 교육자가 총 몇 명인지 중복없이 계산 후 조회
select
	count(distinct author_id) as '과정 등록한 교육자 수'
from courses;

-- sum, avg, max, min
-- grades 등급 테이블에서 모든 제출물의 총점과 평균 점수를 조회하라.
select
	sum(score) as '총점',
    avg(score) as '평균'
from grades;

-- 최고, 최저 점수
select
	max(score) as '최고 점수',
    min(score) as '최저 점수'
from grades;

-- [에러케이스] 비집계 컬럼과 집계함수 동시 사용 시 발생
-- 집계함수는 여러 행의 값을 하나의 결과로 요약한다.
-- 하지만 select 절에 집계 함수와 함께 Group by 절 없이 일반 컬럼(비집계)을
-- 함께 명시하게 되면, 어떤 name 값을 count(*) 와 함께 묶어야 할 지 알 수 없어 에러가 발생한다.
select
	name, 
	count(*) as '총 사용자수'
from users;

-- Group by
-- 테이블의 데이터를 특정 컬럼을 기준으로 그룹을 묶고,
-- 각 그룹에 대해 집계 함수를 적용하여 소계를 계산하는 기능이다.

-- users 테이블에서 status 별로 사용자 몇 명인지 조회
select
	status,
    count(*) as '사용자 수'
from users
group by status;

-- courses 테이블에서 각 교육자가 몇 개의 과정을 개설했는지 조회해라
select
	author_id,
    count(*) as '개설 강좌 수'
from courses
group by author_id;

-- having 절
-- having 절은 where 절 처럼 조건을 작성할 때 기술한다.
-- 가장 큰 차이점은 where는 데이터를 그룹화 하기 전 필터링
-- having 절은 그룹화 후 집계 함수를 계산한 후에 필터링 한다.

-- 개설 강좌 수가 3개 이상인 author_id 와 개설 강좌 수 조회
select
	author_id,
    count(*) as '개설 강좌 수'
from courses
group by author_id
having count(*) >= 3;

-- enrollments 테이블
-- 'completed' 상태인 과정 중에서, 2명 이상의 학생이 등록한 과정(course_id)과
-- 해당 학생 수를 조회한다. (where , having)
select * from enrollments;
select 
	course_id,
    count(*) as '등록학생수'
from enrollments
where status = 'completed'
group by course_id
having count(*) >= 2
order by count(*) desc;

-- users 테이블
-- 2024 년에 가입한 사용자들 중에서, 월별 가입자 수가 15명 이상인 월과
-- 해당 월의 가입자 수를 조회한다.

-- 문제 상황 제기
-- 월별로 데이터를 그룹화 해야 하는데 실제 created_at 컬럼의 데이터는 일과 시간도 작성되어 있다.
-- 따라서 우리는 created_at 컬럼의 데이터를 format 후 그룹화 할 것이다.
-- 내장 함수 맛보기 
-- DATE_FORMAT(날짜, 형식) : 날짜 데이터를 원하는 문자열 형식으로 바꿔주는 함수
select
	date_format(created_at, '%Y-%m') as '가입 월',
    count(*) as '가입자 수'
from users
where 
	created_at >= '2024-01-01' and created_at < '2025-01-01'
group by `가입 월`
having `가입자 수` >= 15;

