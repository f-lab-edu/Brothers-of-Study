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

- [#1 분산 환경에서 발생하는 세션 불일치 문제 해결하기.](https://medium.com/@ukjjang/%EB%B6%84%EC%82%B0-%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-%EB%B0%9C%EC%83%9D%ED%95%98%EB%8A%94-%EC%84%B8%EC%85%98-%EB%B6%88%EC%9D%BC%EC%B9%98-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0-672df7a35366?source=user_profile_page---------7-------------5a935396acb1---------------)
- [#2 캐싱적용해서 읽기 작업 성능 개선하기.](https://medium.com/@ukjjang/%EC%BA%90%EC%8B%B1%EC%A0%81%EC%9A%A9%ED%95%B4%EC%84%9C-%EC%A1%B0%ED%9A%8C-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0%ED%95%98%EA%B8%B0-6ec57dd4e245)
- [#3 중복 예약으로 발생하는 DB 동시성 문제 해결하기. (팬텀 리드 이슈)](https://medium.com/@ukjjang/%EC%A4%91%EB%B3%B5-%EC%98%88%EC%95%BD%EC%9A%94%EC%B2%AD%EC%9C%BC%EB%A1%9C-%EB%B0%9C%EC%83%9D%ED%95%98%EB%8A%94-db-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0-%ED%8C%AC%ED%85%80-%EB%A6%AC%EB%93%9C-%EC%9D%B4%EC%8A%88-5f52572c6e0f?source=user_profile_page---------2-------------5a935396acb1---------------)
- [#4 Jenkins를 통한 CI & CD 구축하기.](https://medium.com/@ukjjang/jenkins%EB%A5%BC-%ED%86%B5%ED%95%9C-ci-cd-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0-3573a9fd4188?source=user_profile_page---------5-------------5a935396acb1---------------)
- [#5 분산환경에서 서비스의 중단없이 배포하기.](https://medium.com/@ukjjang/%EB%B6%84%EC%82%B0%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-%EC%84%9C%EB%B9%84%EC%8A%A4%EC%9D%98-%EC%A4%91%EB%8B%A8%EC%97%86%EC%9D%B4-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0-47fa97086a15?source=user_profile_page---------4-------------5a935396acb1---------------)
- [#6 MySQL Replication Master/Slave 이중화 적용하기.](https://medium.com/@ukjjang/mysql-replication-master-slave-%EC%9D%B4%EC%A4%91%ED%99%94-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0-5b8088e008b8)

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

## Api Docs

<https://jinukix.github.io/bos-api-guide/>

## Use cases

<https://github.com/f-lab-edu/Brothers-of-Study/wiki/Use-cases>

## ERD

<https://github.com/f-lab-edu/Brothers-of-Study/wiki/ERD>

![Brothers-of-Study (1)](https://user-images.githubusercontent.com/74256905/149701721-660ed9aa-c8d8-4042-83bc-a05c51a5b761.png)

## Prototyping

<https://github.com/f-lab-edu/Brothers-of-Study/wiki/Prototyping>
