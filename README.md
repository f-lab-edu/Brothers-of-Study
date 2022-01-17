<img width="1057" alt="147902104-959468cb-fdc0-4972-9a2f-03bc2227922d" src="https://user-images.githubusercontent.com/74256905/148158835-d8a3e76a-eb67-4340-bd55-621fa1e18c59.png">

# 📚 Brothers-of-Study
- 스터디 카페 예약 서비스인 **스터디의 형제들**입니다.
- **대규모 트래픽**을 처리할 수 있는 서비스를 목표로 진행했습니다. 
- 성능 및 유지보수성을 고려해 **객체지향적인 코드**를 작성하려 노력했습니다.
- 클라이언트는 프로토타입으로 대체하여 **백엔드 개발**에 초점을 맞춰 진행했습니다.

## 사용 기술
- Java 11
- Spring Boot
- MySQL
- MyBatis
- Redis
- Jenkins

## Issue

- [#1 분산 환경에서 발생하는 세션 불일치 문제 해결하기.](https://jinukix.tistory.com/127)
- [#2 DTO, Entitiy간의 변환은 어디서 일어나야 할까? 프로젝트 구조에 대한 고찰.](https://jinukix.tistory.com/135)
- [#3 AOP를 적용해 중복되는 로그인 검증 기능 분리하기.](https://jinukix.tistory.com/133)
- [#4 캐싱적용해서 읽기 작업 성능 개선하기.](https://jinukix.tistory.com/136)
- [#5 중복 예약으로 발생하는 DB 동시성 문제 해결하기. (팬텀 리드 이슈)](https://jinukix.tistory.com/137)
- [#6 Jenkins를 통한 CI & CD 구축하기.](https://jinukix.tistory.com/138)
- [#7 분산환경에서 서비스의 중단없이 배포하기.](https://jinukix.tistory.com/139)
- [#8 MySQL Replication Master/Slave 이중화 적용하기.](https://jinukix.tistory.com/140)

## 프로젝트 관리

commit 요청시 자동 Build 및 Test를 적용했습니다.

jacoco 플러그인을 통해 Controller 레이어를 제외한 모든 영역의 Test Coverage 100%를 유지했습니다.

코드 컨벤션은 Google code Style을 준수해서 작성했습니다.

checkstyle 플러그인을 적용해 코드 컨벤션을 유지했습니다.

### InteliJ Google Style 적용 방법

1. Preferences > Editor > Code Style > Java 메뉴
2. Scheme 우측 메뉴에서 Import Scheme > InteliJ IDEA code style XML
3. checkstyle/intellij-java-google-style.xml 파일 선택
4. 적용 후 Tab size 와 Indent 를 4로 변경

## Use cases

<https://github.com/f-lab-edu/Brothers-of-Study/wiki/Use-cases>

## ERD

<https://github.com/f-lab-edu/Brothers-of-Study/wiki/ERD>

![Brothers-of-Study (1)](https://user-images.githubusercontent.com/74256905/149701721-660ed9aa-c8d8-4042-83bc-a05c51a5b761.png)

## Prototyping

<https://github.com/f-lab-edu/Brothers-of-Study/wiki/Prototyping>
