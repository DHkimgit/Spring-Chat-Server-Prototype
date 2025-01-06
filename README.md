# 스프링 STOMP 채팅 서버 프로토타입

채팅 서버 개발을 위한 테스트용 프로토타입 어플리케이션입니다.

개발중. 기본 기능 구현 후 모듈 규칙에 따라 리팩토링 예정

- service 모듈에서 redis 의존성 분리
- 도메인 모듈 사용

# 모듈
## module-domain
**도메인 모델 정의, Repository 인터페이스, 도메인 이벤트 정의**

### 모듈 책임
- POJO 도메인 모듈
- 모듈 간 정보 전달 공통 인터페이스

### 기술 스택
- Spring Web Context(Repository 인터페이스 빈 등록)

### 협력 관계
- **모든 모듈(compileonly)** : 모듈 간 정보 전달 공통 인터페이스

### Convention & Rules
#### 공통 규칙
- 외부 시스템과의 직접적 통신 금지
- 공용 모듈을 제외한 다른 모듈 의존 금지

## module-External-API 모듈
**외부 클라이언트와의 통신을 담당하는 모듈**

### 모듈 책임
- RESTful API 및 WebSocket 엔드포인트 제공
- HTTP 요청/응답, 인증/인가 처리
- 클라이언트 요청을 서비스 모듈(module-service)로 전달
- API 버전 관리
- 클라이언트 통신 DTO 관리

### 기술 스택
- Spring Web (REST API)
- Spring WebSocket (STOMP 기반 WebSocket 엔드포인트)

### 협력 관계
- **module-domain**: 클라이언트 요청을 비즈니스 로직에 위임

### 공통 규칙
- 스토리지 모듈(module-storage-?) 직접 호출 금지
- DTO 다른 모듈로 전달 금지. 도메인 모듈(module-domain) 객체로 바꿔서 전달

## module-service
**애플리케이션 핵심 비즈니스 로직 처리**

### 모듈 책임
- 채팅 메시지 전송, 채팅방 생성/관리, 사용자 연결 관리 등 핵심 로직 구현
- 메시지 저장 요청을 저장소 모듈(module-storage-rdb, module-storage-redis)에 위임
- 비즈니스 규칙 적용(메시지 필터링 등)
- WebSocket 이벤트를 처리하여 클라이언트와의 상호작용 관리

### 기술 스택
- Spring Web
- Spring WebSocket

### 협력 관계
- **module-external-api**: 클라이언트 요청에 대한 비즈니스 로직 처리.
- **module-domain(compile only)** : 스토리지 모듈 인터페이스 제공
- **module-storage-rdb(runtime only)**: 메시지 영구 저장 요청.
- **module-storage-redis(runtime only)** : Redis를 통한 Pub/Sub 브로드캐스팅 및 캐시 관리.

### Convention & Rules

#### 공통 규칙
- 구현시 Business 레이어와 Implementation 레이어 구분 지향 하기
- 스토리지 모듈(module-storage)은 런타임으로 의존. 구현 시 도메인 모듈(module-domain)의 추상화된 Repository 명세 사용

## module-Storage-rdb

**RDBMS(MySQL) 통한 데이터 영구 저장 및 조회 인터페이스**

### 모듈 책임
- 채팅 메시지, 채팅방 정보, 사용자 정보 등 영구적으로 유지해야 하는 데이터 관리.
- 데이터베이스 연결 관리 및 최적화.
- 도메인 모듈의 리포지토리 명세 구현.

### 기술 스택
- Spring Data JPA
- Hibernate
- MySQL

### 협력 관계
- **module-domain(compile only)** : repository 인터페이스 제공
