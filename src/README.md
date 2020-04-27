#### 1-1 초난감 DAO
###### 코드 추가
    1. add, get 두개의 메소드 생성
    2. add, get 메소드를 검증하기 위한 테스트 코드 작성
###### gradle 추가    
    mysql 연결
    compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.15'
###### 문제점
    Windows Intellij 콘솔 창에 한글이 깨져서 나옴
        - JetBrains / bin / vm.options 파일 둘다 -Dfile.encoding=UTF-8 추가
        - intellij settings / File Encodings 세 항목 UTF-8로 변경
        - intellij settings / Edit Custom VM options -Dfile.encoding=UTF-8 추가
        - 재부팅
        했지만 아직 해결 안됨
###### 정리
    코드의 기능을 검증하고자 할 때는 오브젝트 스스로 자신을 검증하도록 만들기