- แก้ไขโครงงานรอบสุดท้าย
    - เพิ่ม Polymorphsim
    - ทำ table view ให้เรียงตามวันเวลาล่าสุด
- แก้ไขโครงงาน
    - เปลี่ยน GUI
    - ทำ PDF ใหม่
    - เพิ่ม font 
    - validate input
    - ใส่ alert 
    - update ข้อมูล Staff ทันที
    - เช็คห้องซ้ำเวลา create room
    - clear input หลังจากกด action
    - เพิ่ม polymorphism (down Cast)
- การ commit แต่ละสัปดาห์
    - สัปดาห์ที่ 1 = การสร้างโปรเจ็ค โดย javaFx แบบพื้นฐาน และ เพิ่ม maven to project
    - สัปดาห์ที่ 2 = การสร้าง polymorphism
    - สัปดาห์ที่ 3 = อ่านเขียน csv ไฟล์
    - สัปดาห์ที่ 4 = การทำงานของ admin สามารถสร้าง account ของส่วนกลางได้,สามารถดูการลอกอินของส่วนกลางได้

- การใช้งาน Admin 
    - username : admin
    - password : 1234
    - username : kaew
    - password : 1234
- การใช้งาน Staff
    - Account 1
        - username : patty
        - password : 4321
    - Account 2
        - username : paii
        - password : 1234    
    - Account 3
        - username : fast
        - password : star

- Project Strcuture
- SRC
  - Main
    - 6210406718
        - data
            csv ไฟล์
        - Project.jar รันไฟล์จากตรงนี้
        
    - data
        - room.csv
        - person.csv
        - admin.csv
        - resident.csv
        - item.csv
    - resource
        - css
            - ไฟล์ css
        - fxml
            - เก็บไฟล์ fxml
        - pic
            - เก็บรูปที่ใช้
    - java
        - condo
            - controller
                - adminLoginController
                    - ควบคุมหน้าลอกอินแอดมิน
                - adminPageController
                    - ดูการเข้าใช้งานของส่วนกลาง
                    - เพิ่มบัญชีผู้ใช้ส่วนกลาง
                    - เปลี่ยนรหัสผ่าน
                - CreditController
                    - ข้อมูลผู้จัดทำ
                - MenuController
                    - ควบคุมหน้าหลัก
                    - Staff
                    - Admin
                    - Credit & credit
                - StaffLoginController
                    - ตรวจสอบการลอกอินของส่วนกลาง
                    - อัพเดทข้อมูลในไฟล์ csv
                - StaffPageController
                    - แสดงห้องพัก
                    - เปลี่ยน รหัสผ่าน
                - StaffRoomManageController
                    - สร้าง ห้อง + ชื่อผู้อยู่อาศัย
                    - แสดงห้องพัก
                    - การเพิ่มข้อมูลห้องพัก
                - StaffMailManageController
                    - เพิ่ม/ถอน จดหมาย
                    - หน้าจดหมายทั้งหมด
            
            - model
                - Building
                    - ตัวอาคารและห้องพัก
                - BuildingList
                    - เก็บ ข้อมูลตัวอาคารและห้อง
                - Item
                    - ตัวจดหมาย
                - ItemList
                    - เก็บ ข้อมูลจดหมาย
                - Person
                    - บัญชีที่สามารถเก็บ admin และ staff
                - PersonList
                    - เก็บบัญชี Person
                
            - service
                - FileDataSource
                    - interface ใช้เรียกฟังก์ชันที่คล้ายกันใน admin,person และ resident filedatasource
                - AdminFileDataSource
                    - การทำของการเปลี่ยนพาสเวิด
                    - การเช็คบัญชีก่อนเข้าสู่ระบบ
                    - อ่านเขียน admin.csv
                - StaffFileDataSource
                    - เปลี่ยนพาสเวิด
                    - อัพเดทและเพิ่มเวลาลอกอินล่าสุด
                    - ตารางลอกอิน
                    - อ่านเขียน person.csv
                - ItemFileDataSource
                    - สร้างจดหมาย,พัสดุ,เอกสาร
                    - อัพเดทตาราง จดหมาย
                    - อัพเดทตาราง ห้องพัก
                    - อ่านขียน item.csv
                - BuildingFileDataSource
                    - สร้างห้อง
                    - ลบห้อง
                    - โชว์ห้อง
            -startup(main program)
- การรัน Jar file ใน Folder 6210406718/6210406718/6210406718.jar    
- กรณี double click jar ไฟล์ไม่ได้ให้ ใช้ git bash ในการรัน โดยใช้คำสั่ง java -jar Project.jar