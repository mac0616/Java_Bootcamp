SELECT * FROM chundb.tb_class;

-- 1. 춘 기술대학교의 학과 이름과 계열을 표시하시오. 단, 출력 헤더는 "학과 명", "계열" 으로 표시하도록 한다.
select
	DEPARTMENT_NO as '학과 명',
    CATEGORY as '계열'
from tb_department;

-- 2. 학과의 학과 정원을 다음과 같은 형태로 화면에 출력핚다.
select
	concat(DEPARTMENT_NAME, '의 정원은 ', CAPACITY, '명 입니다.')
from tb_department;


-- 3. "국어국문학과" 에 다니는 여학생 중 현재 휴학중인 여학생을 찾아달라는 요청이 들어왔다. 누구인가? 
-- (국문학과의 '학과코드'는 학과 테이블(TB_DEPARTMENT)을 조회해서 찾아 내도록 하자)
select
	ts.STUDENT_NAME
from tb_department as tb
join tb_student as ts on tb.DEPARTMENT_NO = ts.DEPARTMENT_NO
where 
	tb.DEPARTMENT_NAME='국어국문학과' 
    and
    ts.ABSENCE_YN = 'Y'
    and 
    (substring(ts.STUDENT_SSN, 8, 1) = 2 or substring(ts.STUDENT_SSN, 8, 1) = 4);


-- 4. 도서관에서 대출 도서 장기 연체자 들을 찾아 이름을 게시하고자 핚다. 그 대상자들의 학번이 다음과 같을 때 대상자들을 찾는 적젃핚 SQL 구문을 작성하시오.
-- A513079, A513090, A513091, A513110, A513119
select
	STUDENT_NAME
from TB_STUDENT
where STUDENT_NO in ('A513079', 'A513090', 'A513091', 'A513110', 'A513119')
order by STUDENT_NAME desc;

-- 5. 입학정원이 20 명 이상 30 명 이하인 학과들의 학과 이름과 계열을 출력하시오.
select
	DEPARTMENT_NAME,
    CATEGORY
from tb_department
where CAPACITY >=20 and CAPACITY <=30;

-- 6. 춘 기술대학교는 총장을 제외하고 모든 교수들이 소속 학과를 가지고 있다. 그럼 춘기술대학교 총장의 이름을 알아낼 수 있는 SQL 문장을 작성하시오.
select
	PROFESSOR_NAME
from tb_professor
where DEPARTMENT_NO is null;

-- 7. 혹시 젂산상의 착오로 학과가 지정되어 있지 않은 학생이 있는지 확인하고자 핚다. 어떠핚 SQL 문장을 사용하면 될 것인지 작성하시오.
select
	STUDENT_NAME
from tb_student
where DEPARTMENT_NO is null;

-- 8. 수강신청을 하려고 핚다. 선수과목 여부를 확인해야 하는데, 선수과목이 존재하는 과목들은 어떤 과목인지 과목번호를 조회해보시오.
select
	CLASS_NO
from tb_class
where PREATTENDING_CLASS_NO is not null;

-- 9. 춘 대학에는 어떤 계열(CATEGORY)들이 있는지 조회해보시오.
select
	distinct category
from tb_department
order by category asc;

-- 10. 19 학번 전주 거주자들의 모임을 만들려고 한다. 휴학한 사람들은 제외하고, 재학중인 학생들의 학번, 이름, 주민번호를 출력하는 구문을 작성하시오.
select
	STUDENT_NO,
    STUDENT_NAME,
    STUDENT_SSN
from tb_student
-- where substring(ENTRANCE_DATE, 1, 4) = '2019' 
where year(ENTRANCE_DATE) = '2019'
and substring(STUDENT_ADDRESS, 1, 2) = '전주'
and ABSENCE_YN = 'N'
order by STUDENT_NAME;

-- ============================================LEVEL2===============================================
-- 1. 영어영문학과(학과코드 `002`) 학생들의 학번과 이름, 입학 년도를 입학 년도가 빠른순으로 표시하는 SQL 문장을 작성하시오.
-- ( 단, 헤더는 "학번", "이름", "입학년도" 가 표시되도록 한다.)
select
	ts.STUDENT_NO as '학번',
    ts.STUDENT_NAME as '이름',
    ts.ENTRANCE_DATE as '입학년도'
from tb_department as tb
join tb_student as ts on tb.DEPARTMENT_NO = ts.DEPARTMENT_NO
where tb.DEPARTMENT_NO = '002'
order by ts.ENTRANCE_DATE;

-- 2. 춘 기술대학교의 교수 중 이름이 세 글자가 아닌 교수가 두 명 있다고 한다. 그 교수의 이름과 주민번호를 화면에 출력하는 SQL 문장을 작성해 보자. 
select
	PROFESSOR_NAME,
    PROFESSOR_SSN
from tb_professor
where PROFESSOR_NAME not like '___';

-- 3. 춘 기술대학교의 남자 교수들의 이름과 나이를 출력하는 SQL 문장을 작성하시오. 단 이때 나이가 적은 사람에서 많은 사람 순서로 화면에 출력되도록 만드시오. 
-- (단, 교수 중 2000 년 이후 출생자는 없으며 출력 헤더는 "교수이름", "나이"로 한다. 나이는 ‘만’으로 계산한다.)
select
	PROFESSOR_NAME,
    TIMESTAMPDIFF(YEAR, STR_TO_DATE(CONCAT('19', substring(PROFESSOR_SSN, 1, 6)), '%Y%m%d'), NOW()) AS '나이'
from tb_professor
where substring(PROFESSOR_SSN, 8, 1) = 1
order by `나이`;

-- 4. 교수들의 이름 중 성을 제외한 이름만 출력하는 SQL 문장을 작성하시오. 출력 헤더는’이름’ 이 찍히도록 핚다. (성이 2 자인 경우는 교수는 없다고 가정하시오)
select
	substring(PROFESSOR_NAME, 2) as '이름'
from tb_professor;
    
-- 5. 춘 기술대학교의 재수생 입학자를 구하려고 한다. 어떻게 찾아낼 것인가? 이때, 만 19살이 되는 해에 입학하면 재수를 하지 않은 것으로 간주한다.
select
	STUDENT_NO,
    STUDENT_NAME
from tb_student
where 
year(ENTRANCE_DATE) - (case
	when substring(STUDENT_SSN,1,2)>=26 then concat(19, substring(STUDENT_SSN,1,2))
	else concat(20, substring(STUDENT_SSN,1,2))
end) >19;
    
-- 6. 2020년 크리스마스는 무슨 요일이었는가?    
select  case weekday('2020-12-25')
	when '0' then '월요일'
	when '1' then '화요일'
	when '2' then '수요일'
	when '3' then '목요일'
	when '4' then '금요일'
	when '5' then '토요일'
else '일요일'
end as '요일';

-- 7. 4. STR_TO_DATE('99/10/11', '%y/%m/%d') / STR_TO_DATE('49/10/11', '%y/%m/%d')`은 각각 몇 년 몇 월 몇 일을 의미할까? 
-- 또 STR_TO_DATE*('70/10/11', '%y/%m/%d') / STR_TO_DATE('69/10/11', '%y/%m/%d')` 은 각각 몇 년 몇 월 몇 일을 의미할까?
select STR_TO_DATE('99/10/11',  '%y/%m/%d');
select STR_TO_DATE('49/10/11', '%y/%m/%d');	-- 2049-101-11
    
-- 8. 학번이 A517178 인 한아름 학생의 학점 총 평점을 구하는 SQL 문을 작성하시오. 
-- 단, 이때 출력 화면의 헤더는 "평점" 이라고 찍히게 하고, 점수는 반올림하여 소수점 이하 한자리까지만 표시한다.
select
	round(avg(POINT), 1) as '평점'
from tb_grade
where
	STUDENT_NO = 'A517178';
    
-- 9. 학과별 학생수를 구하여 "학과번호", "학생수(명)" 의 형태로 헤더를 만들어 결과값이 출력되도록 하시오.
select
	distinct DEPARTMENT_NO as '학과번호',
	count(DEPARTMENT_NO) as '학생수(명)'
from tb_student
group by DEPARTMENT_NO;
    
-- 10. 지도 교수를 배정받지 못한 학생의 수는 몇 명 정도 되는 알아내는 SQL 문을 작성하시오.
select
	count(*)
from tb_student
where COACH_PROFESSOR_NO is null;
    
-- 11. 학번이 A112113 인 김고운 학생의 년도 별 평점을 구하는 SQL 문을 작성하시오. 
-- 단, 이때 출력 화면의 헤더는 "년도", "년도 별 평점" 이라고 찍히게 하고, 점수는 반올림하여 소수점 이하 한자리까지만 표시한다.
select 
    substring(TERM_NO, 1, 4) as '년도',
    round(avg(POINT), 1) as '년도 별 평점'
from tb_grade
where STUDENT_NO = 'A112113'
group by `년도`;

-- 12. 학과 별 휴학생 수를 파악하고자 한다. 학과 번호와 휴학생 수를 표시하는 SQL 문장을 작성하시오.
select
    DEPARTMENT_NO as '학과번호',
    sum(case when ABSENCE_YN = 'Y' then 1 else 0 end) as '휴학생 수'
from tb_student
group by DEPARTMENT_NO
order by DEPARTMENT_NO;
    
-- 13. 춘 대학교에 다니는 동명이인(同名異人) 학생들의 이름을 찾고자 한다. 어떤 SQL 문장을 사용하면 가능하겠는가?        
select
	distinct STUDENT_NAME as '동명이름',
    count(STUDENT_NAME) as '동명인 수'
from tb_student
group by STUDENT_NAME
having count(STUDENT_NAME) >= 2
order by STUDENT_NAME;

-- 14. 학번이 A112113인 김고운 학생의 년도, 학기 별 평점과 년도 별 누적 평점 , 총평점을 구하는 SQL 문을 작성하시오. (단, 평점은 소수점 1 자리까지만 반올림하여 표시한다.



