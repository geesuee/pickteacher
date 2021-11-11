# 👩‍🏫 PickTeacher 👨‍🏫

![12)](https://user-images.githubusercontent.com/68639271/137893483-1d6167f6-9e52-41d9-8ec8-302cacd0c8b3.gif)
Spring Boot, Vue.js 기반 학생과 선생님 매칭 웹 프로젝트
<br><br>

## 🤹‍♀️ Team FourJ 🤹‍♂️
- [김지명](https://github.com/ji-myeung-Kim)  🐂 Ji
- [마유진](https://github.com/YoojinMa)  🐎 Jin
- [배지수](https://github.com/geesuee)  🦘 Jee
- [서주연](http://github.com/do-oni)  🦙 Ju
 
<br><br>
# ⚙ 기술 스택

language
> `Java8 (JDK 1.8)`
`HTML5/CSS/JavaScript`
> 

framework

> `Spring Boot(2.5.5)`
> 

library

> `Vue.js`
`axios`
> 

database

> `Oracle Database`
> 

IDE

> `spring tool suite(STS 3.9.14)`
`visual studiocode`
> 

others

> `postman`
`ERDcloud`
`channeltalk API`
> 

<br><br>

# 🗂 DataBase 구조

![https://user-images.githubusercontent.com/68639271/137882463-cc3d1084-a332-4a5d-a302-098da087705b.png](https://user-images.githubusercontent.com/68639271/137882463-cc3d1084-a332-4a5d-a302-098da087705b.png)

<br><br>

# 📜 기능명세서

![image](https://user-images.githubusercontent.com/68639271/141271112-35215bbb-495f-4c10-a4bc-3ca89d522733.png)
![image](https://user-images.githubusercontent.com/68639271/141271467-3a240dba-66ba-4349-8026-1ec3270589ec.png)

<br><br>

# 🗓 Time Table
![image](https://user-images.githubusercontent.com/68639271/137893709-143a0dde-5299-4fc2-8d40-9626a88ff8e8.png)
[중간 프로젝트](https://www.notion.so/a2760ac5af1048bb8e29a7d7c71d9804)

<br><br>

## 🖇 세부 진행 사항

- GITHUB 프로젝트 이용
[https://github.com/fourJ/pickteacher/projects/1](https://github.com/fourJ/pickteacher/projects/1)

<br><br>

## 🌋 이슈

- [https://github.com/fourJ/pickteacher/issues](https://github.com/fourJ/pickteacher/issues)
- 소소한 이슈
    - 10/11
        - invalid Identifier
            
            [원인] DB table명 like로 했을 때
            [해결] likes로 변경
            
        - @RequiredArgsConstructor
            
            @RequiredArgsConstructor애노테이션은 자신이 원하는 형태의 생성자로 사용할 수 있게끔 해주는 애노테이션
            
    - 10/18
        - 415 UTF-8 에러
        form 태그는 delete와 put이 불가능함. hidden input 태그의 value를 put으로 설정해서 form 안에 넣어줬는데 계속 415 에러가 남
            
            [원인] 스프링 부트 2.2 이상버전에서는 자동으로 구성되지 않음
            [해결]  application.properties에 설정 값을 넣어줘야 함. spring.mvc.hiddenmethod.filter.enabled=true
