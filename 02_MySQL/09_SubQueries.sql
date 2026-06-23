-- 쿼리 내부에 또 다른 쿼리

-- SQL 기초 강의를 수강하는 학생들의 평균 점수보다 높은 점수를 받은 학생의 이름은 무엇인가?
-- (평균 점수 모름. -> 평균 구하고 높은 점수인 학생 조회)

-- 1. SQL 기초 강의 수강생들의 평균 점수 구하기 ex) 평균 85
-- 2. 1단계에서 도출된 85점 보다 높은 점수를 받은 학생 조회

-- where 조건절에서 사용하는 서브쿼리
-- '홍길동' 사용자와 같은 해에 가입한 모든 `사용자의 이름과 가입일 조회` (users 테이블)
-- 1. (` `만 해결)
select
	name,
    created_at
from users;

-- 만약 홍길동이 24년에 가입했다는 조건이 있다면 문제를 풀 수 있지만, 없음.
-- 2. 홍길동의 가입 년도 파악
select
	year(created_at)
from users
where name = '홍길동';

-- 1단계, 2단계를 하나의 쿼리문으로 변경해보자.
-- 해당 식은 '단일 행 서브쿼리'라고 불린다.
-- 서브쿼리가 하나의 행, 하나의 열인 결과를 반환해야 한다.
select
	name,
    created_at
from users
where year(created_at) = (
	select
	year(created_at)
	from users
	where name = '홍길동'
);

-- 다중 행 서브쿼리
-- 서브쿼리가 여러 개의 행을 결과로 반환할 때를 의미한다.
-- in , any , all 과 같은 연산자와 함께 사용한다.

-- 'Python' 태그가 붙은 과정을 수강 신청한 모든 학생의 user_id 와 name, course_id 조회하라.
-- enrollments , tags, courses join이 연산됨.
select
	u.user_id,
    u.name,
	e.course_id
from users u
join enrollments e on u.user_id = e.user_id
where e.course_id in (
	-- 여기서 도출해야 하는 값은 course_id 가 Python 태그를 포함하고 있는 것을 판단.
    select
		c.course_id
	from courses c
    join course_tags ct on (c.course_id = ct.course_id)
    join tags t on (ct.tag_id = t.tag_id)
    where t.name like '%Python%'
);

-- from 절에 사용하는 서븤쿼리 (인라인 View)
-- from 테이블
-- 마치 하나의 테이블처럼 쿼리의 결과를 사용하는 것이다.

-- 각 교육자별로 개설한 과정 수를 계산한 결과를 가상 테이블로 만들고,
-- 이 가상 테이블과 users 테이블을 조인하여, 교육자 이름과 개설 과정 수를 조회하라.
select
	u.name as '교육자 이름',
    author_stats.course_count as '개설 과정 수'		-- 여기에도 select문 쓸 수 있지만 성능 떨어짐. 쓰지말 것!
from users u
join  (
	select
		author_id,
        count(*) as course_count
	from courses
	group by author_id
) as author_stats on u.user_id = author_stats.author_id
order by `개설 과정 수` desc;
