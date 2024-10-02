
## 엔티티 간의 관계 설명
![image](https://github.com/user-attachments/assets/207fd865-6705-4070-a668-fd99c60f5b66)


### User
사용자 정보를 저장하는 엔티티입니다.  
- **id**: 기본 키 (Primary Key)  
- **name**: 사용자 이름  

하나의 사용자는 여러 개의 강의에 수강 신청할 수 있습니다.

### Lecture
강의 정보를 저장하는 엔티티입니다.  
- **id**: 기본 키 (Primary Key)  
- **title**: 강의 제목  
- **description**: 강의 설명  
- **instructor**: 강사 이름  
- **enrollmentCount**: 현재 수강 인원  
- **capacity**: 최대 수강 인원  
- **startTime**: 강의 시작 시간  

하나의 강의에는 여러 명의 사용자가 수강 신청할 수 있습니다.

### Enrollment
수강 신청 정보를 저장하는 엔티티입니다.  
- **userId**: 사용자 (User)와의 관계를 나타내는 외래 키 (Foreign Key)  
- **lectureId**: 강의 (Lecture)와의 관계를 나타내는 외래 키 (Foreign Key)  
- **enrollmentDate**: 수강 신청 날짜  

수강 신청 정보는 `User`와 `Lecture` 간의 **다대다(Many-to-Many) 관계**를 나타냅니다.  
즉, 한 사용자는 여러 개의 강의에 수강 신청할 수 있고, 하나의 강의에도 여러 사용자가 수강 신청할 수 있습니다.

## 설계 이유

- **독립적인 수강 신청 관리**  
  `Enrollment` 엔티티를 별도로 두어 사용자가 강의에 수강 신청한 정보를 독립적으로 관리합니다.  
  이를 통해 사용자가 여러 강의에 수강 신청할 수 있고, 한 강의에도 여러 사용자가 수강할 수 있도록 다대다 관계를 효율적으로 처리할 수 있습니다.


## 외래 키 설정을 하지 않은 이유

실제로는 엔티티 간의 외래 키를 데이터베이스 레벨에서 설정하지 않았습니다. 그 이유는 다음과 같습니다:

1. **유연한 데이터 처리**  
   데이터베이스에서 외래 키를 강제하지 않음으로써, 강의나 사용자가 삭제될 때 발생할 수 있는 참조 무결성 제약을 피하고자 했습니다. 이를 통해 삭제 시 연관된 데이터를 수동으로 관리할 수 있는 유연성을 확보할 수 있습니다.

2. **성능 최적화**  
   외래 키를 사용하면 데이터베이스에서 참조 무결성을 유지하기 위해 추가적인 검사를 수행하게 됩니다. 제약 조건이 성능 저하를 일으킬 수 있어, 외래 키를 설정하지 않음으로써 성능을 최적화하려 했습니다.
