/*
TABLES INFOS
*/

--유저 테이블 (유저아이디 pk, 성, 이름, 비밀번호, 이메일)

--영화 테이블 (영화id pk, 제목, 감독, 개봉일, 포스터이미지 경로)

--상영관 테이블 (상영관 id pk, 상영관이름)

--상영관(s1, s2,..) 별 좌석 테이블 (상영관 id fk, 좌석 id pk)

--상영 영화 테이블 (상영id pk, 영화 id fk, 상영일, 상영시간, 상영관id fk)

--상영관 좌석 별 예매상태 테이블 (상영관id fk, (상영id, 좌석id) pk, 예매여부)

--예매내역 테이블 (예매번호 pk, 유저아이디 fk, 상영id fk, 상영관 id, fk, 좌석 id fk , 예매시간) 



/*
CREATE TABLES
*/

create table users (
    user_id varchar2(10 char) primary key,
    user_last_name varchar2(10 char),
    user_first_name varchar2(10 char),
    user_password varchar2(10 char) not null,
    user_email varchar2(20 char) unique
);

create table movies (
    movie_id varchar2(10 char) primary key,
    movie_title varchar2(20 char) not null,
    movie_director varchar2(20 char) not null,
    movie_release varchar2(15 char),
    movie_poster varchar2(20 char)
);

create table screens(
    screen_id varchar2(5 char) primary key,
    screen_name varchar2(10 char) unique
);

create table seats_s1(
    screen_id varchar2(5 char) references screens(screen_id),
    seat_id varchar2(5 char) primary key
);

create table screenings (
    screening_id varchar2(10 char) primary key,
    movie_id varchar2(10 char) references movies(movie_id),
    screening_date varchar2(15 char),
    screening_time varchar2(5 char),
    screen_id varchar2(5 char) references screens(screen_id)
);

create table seats_s1_status(
    screen_id varchar2(5 char) references screens(screen_id),
    screening_id varchar2(10 char) references screenings(screening_id),
    seat_id varchar2(5 char),
    is_reserved varchar2(1 char) not null,
    primary key(screening_id,seat_id)
);

create table booking_status (
    booking_id number(20) generated as identity,
    user_id varchar2(10 char) references users (user_id),
    screening_id varchar2(10 char) references screenings(screening_id),
    screen_id varchar2(5 char) references screens(screen_id),
    seat_id varchar2(5 char) references seats_s1(seat_id),
    booking_Time timestamp,
    constraint pk_booking_status_id primary key (booking_id)
);


/*
INSERT 
*/


--USERS
insert into users (user_id, user_last_name, user_first_name, user_password, user_email)
    values('kinolee', 'lee', 'subin', 'kinolee','subin@gmail.com');

--MOVIES
insert into movies (movie_id, movie_title, movie_director, movie_release, movie_poster)
    values('m1','위키드(Wicked)','존 추(John M.Chu)','2024/11/20','posters/Wicked.jpg');
insert into movies (movie_id, movie_title, movie_director, movie_release, movie_poster)
    values('m2','이처럼 사소한 것들','팀 말란츠','2024/12/11','posters/STLT.jpg');
insert into movies (movie_id, movie_title, movie_director, movie_release, movie_poster)
    values('m3','무파사 라이온킹','베리 젠킨스','2024/12/18','posters/LionKing.jpg');
insert into movies (movie_id, movie_title, movie_director, movie_release, movie_poster)
    values('m4','하얼빈','우민호','2024/12/25','posters/HB.jpg');
insert into movies (movie_id, movie_title, movie_director, movie_release, movie_poster)
    values('m5','모아나2','데이브 데릭 주니어','2024/11/27','posters/Moana2.jpg');
insert into movies (movie_id, movie_title, movie_director, movie_release, movie_poster)
    values('m6','글래디에이터2','리들리 스콧','2024/11/13','posters/GA.jpg');

--SCREENS
insert into screens (screen_id, screen_name) 
    values('s1','상영관A');

--SEATS_S1
insert into seats_s1 (screen_id,seat_id)
    values('s1','a1');
insert into seats_s1 (screen_id,seat_id)
    values('s1','a2');
insert into seats_s1 (screen_id,seat_id)
    values('s1','a3');
insert into seats_s1 (screen_id,seat_id)
    values('s1','a4');
insert into seats_s1 (screen_id,seat_id)
    values('s1','a5');
insert into seats_s1 (screen_id,seat_id)
    values('s1','a6');
insert into seats_s1 (screen_id,seat_id)
    values('s1','a7');
insert into seats_s1 (screen_id,seat_id)
    values('s1','b1');
insert into seats_s1 (screen_id,seat_id)
    values('s1','b2');
insert into seats_s1 (screen_id,seat_id)
    values('s1','b3');
insert into seats_s1 (screen_id,seat_id)
    values('s1','b4');
insert into seats_s1 (screen_id,seat_id)
    values('s1','b5');
insert into seats_s1 (screen_id,seat_id)
    values('s1','b6');
insert into seats_s1 (screen_id,seat_id)
    values('s1','b7');
insert into seats_s1 (screen_id,seat_id)
    values('s1','c1');
insert into seats_s1 (screen_id,seat_id)
    values('s1','c2');
insert into seats_s1 (screen_id,seat_id)
    values('s1','c3');
insert into seats_s1 (screen_id,seat_id)
    values('s1','c4');
insert into seats_s1 (screen_id,seat_id)
    values('s1','c5');
insert into seats_s1 (screen_id,seat_id)
    values('s1','c6');
insert into seats_s1 (screen_id,seat_id)
    values('s1','c7');

--SCREENINGS
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m1s1','m1','2024/12/02','09:00','s1');
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m2s1','m1','2024/12/02','11:00','s1');
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m3s1','m1','2024/12/02','15:00','s1');
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m4s1','m1','2024/12/04','09:00','s1');
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m5s1','m5','2024/12/03','09:00','s1');
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m6s1','m5','2024/12/03','11:00','s1');
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m7s1','m6','2024/12/03','15:00','s1');
insert into screenings (screening_id, movie_id, screening_date, screening_time, screen_id)
    values('m8s1','m6','2024/12/04','11:00','s1');

    
commit;