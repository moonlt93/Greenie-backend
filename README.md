# Greenie-backend

#  [ 프로젝트 개요 ]

| 항목      | 내용                              |
|---------|---------------------------------|
| **프로젝트소개**  | 생활속의 소음트러블을 해소하기위한 서비스          |
| **구성인원**    | 기획자 1명, 디자이너 1명,안드로이드 1명, FE 1명, BE 1명          |
| **개발기간**    | 총 20일 (2023-05-23 ~ 2023-06-13) |
| **구현 기능** | 상품 추천 , 소음분석데이터 관리 api 작성 ,CI/CD 파이프라인 구축 배포완료 |

# [ 프로젝트 설명 ]

## 1. 사용기술 📚
<div align="center">
 
  <span>
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=OpenJDK&logoColor=white"/>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/amazonrds-527FFF?style=flat&logo=amazonrds&logoColor=white"/>
</span>
<br>
<span>
  <img src="https://img.shields.io/badge/nginx-009639?style=flat-square&logo=nginx&logoColor=white"/>
  <img src="https://img.shields.io/badge/amazonec2-FF9900?style=flat&logo=amazonec2&logoColor=white"/>
  <img src="https://img.shields.io/badge/amazons3-569A31?style=flat-square&logo=amazons3&logoColor=white"/>
  <img src="https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white"/>
  <img src="https://img.shields.io/badge/githubactions-2088FF?style=flat-square&logo=githubactions&logoColor=white"/>
</span>
  <br>

</div>
                

## 2. 구현 기능 소개 🎈


- 기술
   - java <br>
    ◻ 내부 클래스를 활용해서 request 클래스로 들어오고 response 클래스로 원하는 변수만 내보내도록 구현하였습니다.<br>
   
    
   - 추천 상품 조회 기능 구현 <br>
    ◻ 분류모델이 분석해준 데이터를 기반으로 소음을 줄이는 상품을 추천하는 기능을 구상하였습니다. <br>
     1. 상품 테이블과 일대다 매핑을 한 해시태그테이블을 생성하여 해당 소음감소에 효과적인 상품을 리스트업하였습니다.<br>
     2. 분석데이터를 통해 들어온 소음이름을 조건으로 해당하는 상품이 있다면 value에 +1 하여 map에 담았습니다.
     3. map에 담은 데이터를 value를 기준으로 내림차순으로 정렬하였습니다.
     4. 정렬한 데이터가 추천하는 사이즈만큼 상품 갯수가 되지 않는다면, random 메서드를 활용해서 추천했던 상품을 제외한 나머지 번호에서 추천리스트에
        상품을 추가하였습니다.
    <br>    
      2. 우선순위를 정하는 map <br>

   ```java
     private void listSizeMaker(List<Node> numberLists, HashSet<Long> set) {

        if (numberLists.size() < 2) {
            int count = 2 - numberLists.size();

            while (count != 0) {

                Long numbers = (long) (Math.random() * 10 + 1);

                if (set.contains(numbers)) {
                    count--;
                    numberLists.add(new Node(numbers, 0));
                    set.remove(numbers);
                }

            }
        }
    }
   ```

<br>
          4. 우선순위를 정하는 map <br>
     
   ```java
     private void listSizeMaker(List<Node> numberLists, HashSet<Long> set) {

        if (numberLists.size() < 2) {
            int count = 2 - numberLists.size();

            while (count != 0) {

                Long numbers = (long) (Math.random() * 10 + 1);

                if (set.contains(numbers)) {
                    count--;
                    numberLists.add(new Node(numbers, 0));
                    set.remove(numbers);
                }

            }
        }
    }
   ```

  <br>
  <hr>

     ◻ 아쉬운점 <br>
     - 상품 갯수가 fix되어 있는것을 알고 있기에 가능한 구현이었습니다. <br>
     - 상품 갯수가 100건 1000건이었다면 조회성능이 크게 저하될 것이라 생각합니다. <br>
     <br>
     ◻ 개선사항 <br>
     - 상품 갯수에 종속적이지 않은 기능 구현. <br>
              
   - 트래픽 핸들링 <br>
     ◻ was 앞단에 nginx web server를 두어 nginx를 거친 요청을 수행하도록 구현하였습니다. <br>
     ◻ 회원가입이 없이 client 요청에 무방비하게 노출이 되어 있다는 생각으로 예기치 못한 상황이 벌어지는 것을 막고자 하였습니다.  <br>
     
