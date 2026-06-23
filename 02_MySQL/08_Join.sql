-- 테이블 연결 (join) !!!!!!!!!중요 100%!!!!!!!!!

-- 테이블 정보는 각각에 테이블에 저장이 되어있다.
-- 테이블을 나누는 것은 정규화라고 하며, 정규화에 의해
-- 비슷한 종류의 데이터끼리 테이블을 구성하게 된다.

-- join 은 이렇게 분리 된 테이블들 중 공통으로 포함하는 값(주로 PK -FK)
-- 을 기준으로 다시 합쳐서 의미 있는 결과 집합을 만들어낸다.

-- inner join (두 테이블 교집합 찾기)
-- 두 테이블을 연결할 때, 양쪽 테이블에 모두 존재하는 데이터만을 보여준다.
-- 수학의 교집합 과 같은 개념

-- 밑에 문제를 풀기 전 확인 작업.
select * from courses;
desc courses;
show create table  courses;
select * from users;
desc users;

-- 별칭
-- ``, '', ""
-- `` : 컬럼명, 테이블명
-- '' : 문자열
-- "" : 문자열

-- courses 테이블, users 테이블을 조합하여 
-- 과정 제목과, 그 과정을 만든 교육자 이름을 조회하라.
select
	c.title as '과정 제목',
    u.name as '교육자 명'
from courses as c
inner join users as u on c.author_id = u.user_id;		-- author_id와 user_id는 PK-FK 관계.

-- 테이블 3개 조인하기
-- enrollments 테이블 기준으로 users, courses 를 연결하여
-- 어떤 학생이 어떤 과정에 등록했는지 학생 이름과 과정 제목과 등록일을 조회하라.
select * from enrollments;		-- 테이블 구조 확인

select
	u.name as '학생 이름',
    c.title as '과정 제목',
    e.enrolled_at as '등록일'
from enrollments as e
inner join users as u on e.user_id = u.user_id
inner join courses as c on e.course_id = c.course_id;

-- left join
-- inner join 은 양쪽에 모두 데이터가 있는 경우만 보여준다. (교집합)
-- 하지만 요구사항이 만약
-- 과정을 하나도 개설하지 않은 교육자를 포함하여, 모든 교육자 목록을 보고 싶다.
select
	c.title as '과정 제목',
    u.name as '교육자 명'
from users as u left join courses as c on c.author_id = u.user_id;
-- left join에서 메인은 from 옆(join의 왼쪽)에 있는 users. 조인은 course임.

-- left join 과 where 절 사용해서
-- 과정을 단 한 번도 개설하지 않은 사용자 조회하라
-- 조회 시 이름 조회
select
    u.name as '교육자 명'
from users as u 
left join courses as c on c.author_id = u.user_id
where c.course_id is null;

-- right join
-- left join 과 반대로, 오른쪽 테이블의 모든 데이터를 우선적으로 기준 삼는다.
-- 왼쪽 테이블의 경우 매칭되는 데이터가 없으면, null 로 표기된다.

-- users 테이블 기준, user_auth_providers 를 right join 하여
-- 모든 사용자 이름과, 연동된 소셜 로그인 제공자(provider)를 조회한다.
-- (소셜 로그인을 하지 않은 사용자는 null)
select
	u.name as '사용자 이름',
    uap.provider as '소셜 로그인 제공자'
from user_auth_providers as uap
right join users as u on uap.user_id = u.user_id;
-- join 옆에 있는 users가 main임.

-- self join
-- 하나의 테이블 안에 계층적인 관계가 포함될 때
-- 예를 들어, 댓글이 있고 대댓글이 있다라고 가정을 하면, 댓글과 대댓글 또한 댓글이다.
-- 하지만 대댓글의 경우 부모 댓글에 대한 정보를 가져야 어떤 댓글의 대댓글인지를 확인할 수 있다.

-- forum_posts 테이블을 사용해서 self join 진행.
-- 대댓글, 댓글 내용을 함께 조회
select
	parent.post_id as '원본 글 아이디',
    child.post_id as '대댓글 아이디',
    parent.content as '원본 글 내용',
    child.content as '대댓글 내용'
from forum_posts as child
inner join forum_posts as parent on child.parent_post_id = parent.post_id;








