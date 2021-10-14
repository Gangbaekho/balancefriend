# 밸런스 프랜즈 김진수 과제 제출 및 참고 내용

## 실행 환경
1. Java, Spring
2. H2 Inmemory DB
3. JPA(Data Persistant Framework)

## 실행 방법
1. 소스 다운로드.
2. 관련 라이브러리 자동 다운로드.
3. src/main/java/BalanceFriendApplication.class의 main 함수 실행.

## Security

1. 유저 생성시 password 필드를 추가시켜 비밀번호를 입력해야합니다.
2. Spring Secuyt & JWT를 이용하여 토큰 기반의 인증 방식을 사용하였습니다.
3. signin 후 얻는 토큰값을 HttpHeader에 Authorization의 value로 넣어주시되, Bearer JWTToken 값을 넣어주시면 됩니다.


## Exception Handling

1. 커스텀 Exception을 만들어 ExceptionHandlingController에서 처리하도록 하였습니다.

## Test

1. API 자동화 테스트를 진행하였으며 총 17개의 테스트 케이스를 작성하였습니다.

이상입니다. 

감사합니다.
