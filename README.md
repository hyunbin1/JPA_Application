# JPA_Application1

### 기능 목록
- 회원 기능:
  - 회원 등록
  - 회원 조회
- 상품 기능:
  - 상품 등록
  - 상품 수정
  - 상품 조회
- 주문 기능
  - 상품 주문
  - 주문 내역 조회
  - 주문 취소
- 기타 요구사항:
  - 상품은 재고 관리가 필요하다.
  - 상품의 종류는 도서, 음반, 영화가 있다.
  - 상품을 카테고리로 구분할 수 있다.
  - 상품 주문시 배송 정보를 입력할 수 있다.

- 예제를 단순히 하기 위해 구현 안하는 기능
  - 로그인과 권한관리X
  - 파라미터 검증과 예워 처리 단순화
  - 상품은 도서만 사용
  - 카테고리는 사용X
  - 배송 정보는 사용X


### 회원 엔티티 분석(ERD)
---
![image](https://github.com/hyunbin1/JPA_Application1/assets/63040492/0e307d91-7bc9-4873-b1db-5491d444bcf8)

### 회원 테이블 분석
---
![image](https://github.com/hyunbin1/JPA_Application1/assets/63040492/3fcbaf48-0925-4d5a-a8bb-b850b2dc7ca6)


### 애플리케이션 아키텍쳐
---
![image](https://github.com/hyunbin1/JPA_Application1/assets/63040492/d4cc37e8-0103-4e21-8c4e-58262ee20a22)

**계층형 구조 사용**
- controller, web: 웹 계층
- service: 비즈니스 로직, 트랜잭션 처리
- repository: JPA를 직접 사용하는 계층, 엔티티 매니저 사용
- domain: 엔티티가 모여있는 계층, 모든 계층에서 사용

**패키지 구조**
- domain
- exception
- repository
- service
- web
