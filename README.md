# 🚉 Busan Subway Project
## 🔍 Overview
![bsp_app_icon](https://github.com/Moon-ymin/BusanSubwayProject/assets/83321379/ff0eea90-6443-40c6-a90d-b479525174a0)
- BSP (2024년 6월 11일 ~ 2024년 6월 27일)
- 부산 지하철 운행 정보 앱 제작
#### 프로젝트 목표 및 목적
- 지하철 노선도의 구현
- 도착 정보를 받지 못하거나 소리를 듣지 못한 상황에도 타이머 기능을 통해 효율적인 승하차 유도
## 팀원 소개
|   Name   | 문영민 | 정에스더 |
| :------: | ----- | ------ |
| Profile  | <img src="https://github.com/Moon-ymin/subway-project/assets/83321379/0ba481db-106f-46ea-9fed-e6dd595ea1e5" width="150px"> |<img src="https://github.com/Moon-ymin/subway-project/assets/83321379/4a5e7f8d-cdc2-4536-9342-d9f489b8eb20" width="150px"> |
| Position | Front - 메인 화면 구현 <br> Back - 경로 알고리즘 | Front - 경로 확인 화면 구현 <br> Back - DB 설계   |
| Git  | [@Moon-ymin](https://github.com/Moon-ymin) | [@EstherOVO](https://github.com/EstherOVO) |
## 📝 Description
#### 기능 구현
![1](https://github.com/Moon-ymin/BusanSubwayProject/assets/83321379/70a1f145-9f9e-4fd3-808c-b63b357dfbc3)
![2](https://github.com/Moon-ymin/BusanSubwayProject/assets/83321379/24f7a88f-d833-4c28-b9a2-04e08c553419)
![3](https://github.com/Moon-ymin/BusanSubwayProject/assets/83321379/b0d6cbde-06f5-45f3-a687-3900118c7c3c)
#### 전체 기능
- 메인 화면
  - 지하철 노선도 축소, 확대, 이동 가능
  - 한 / 영 변환 버전 제공
  - 지하철 노선도 위 역 선택시 경로 선택 가능
  - 검색 창에서 역 이름 검색 가능
- 운행 확인 화면
  - 선택된 경로의 최단시간, 최소환승 경로 정보 제공
  - 한 / 영 변환 버전 제공
  - 총 소요 시간, 이동 역 수 정보 제공
  - 도착 시간까지의 타이머 제공
## 🎨 Design
#### Prototyping in [Figma](https://www.figma.com/proto/UiQQpYoMqnA4P2OYw7OGic/Untitled?node-id=0-1&t=UJ6WzVOE1EuSFaat-1)
![image](https://github.com/Moon-ymin/subway-project/assets/83321379/734880b0-c365-4966-bf3d-f07a4fbc5311)
#### 시스템 구성도
![시스템구조도](https://github.com/Moon-ymin/BusanSubwayProject/assets/83321379/ceb5e334-9002-4c9b-8335-9c8eab5669fe)
#### ERD
![image](https://github.com/Moon-ymin/BusanSubwayProject/assets/83321379/e0331136-6590-4553-ab3d-1dd3fe725a91)
#### Android - FrontEnd
directory
```markdown
📦 Android
├─ src
│  ├─ main
│  │  ├─ java/com/busanit/busan_subway_project
│  │  │  ├─ config
│  │  │  ├─ controlle
│  │  │  ├─ metr
│  │  │  ├─ model
│  │  │  ├─ repo
│  │  │  ├─ service
│  │  │  ├─ BusanSubwayProjectApplication.java
│  │  │  └─ Subway.java
│  │  └─ resources
│  └─ test/java/com/busanit/busan_subway_project
│     └─ BusanSubwayProjectApplicationTests.java
├─ build.gradle
└─ settings.gradle
...
```
#### Server - BackEnd
directory
```markdown
📦 Spring
├─ src
│  └─ main
│     ├─ assets
│     │  └─ station_points.html
│     ├─ java/com/busanit/subway_project
│     │  ├─ adapter
│     │  ├─ alarm
│     │  ├─ fragment
│     │  ├─ helper
│     │  ├─ model
│     │  ├─ retrofit
│     │  ├─ MainActivity.kt
│     │  ├─ RouteCheckActivity.kt
│     │  └─ SplashActivity.kt
│     ├─ res
│     │  ├─ drawable
│     │  ├─ font
│     │  ├─ layout
│     │  ├─ menu
│     │  ├─ values
│     │  └─ xml
│     ├─ AndroidManifest.x
│     ├─ bsp_app_icon-playstore.png
│     └─ ic_launcher-playstore.png
├─ build.gradle
├─ gradle.properties
└─ settings.gradle
...
```
![IMG_3816](https://github.com/Moon-ymin/BusanSubwayProject/assets/83321379/1ca8c995-0645-49c4-a746-dea370ea7917)
