# 慧医数字医疗应用系统（后端）

基于 Spring Boot 2.5.3 + MyBatis + Spring Security + JWT 的数字医疗应用系统后端，对应课程
「项目开发 → 后端编码实现」任务 1~19。

## 技术栈

- Spring Boot 2.5.3 / Java 17
- MyBatis + PageHelper 分页
- Spring Security + JWT（无状态登录）
- Swagger2 在线接口文档
- MySQL 8 + Druid 连接池

## 运行

1. 确认 MySQL 已启动，库名为 `bin_text`（表结构已存在）。
2. 数据库账号密码在 `src/main/resources/application.yml`，也可用环境变量覆盖：

   ```powershell
   $env:DB_USERNAME='root'
   $env:DB_PASSWORD='你的密码'
   ```

3. 启动：

   ```powershell
   mvn spring-boot:run
   ```

   默认端口 `8080`，接口文档：<http://localhost:8080/swagger-ui.html>

## 登录与鉴权

```powershell
# 登录（账号 admin_1 / 密码 admin）
curl.exe -s -X POST "http://127.0.0.1:8080/api/login" -d "username=admin_1&password=admin"

# 之后所有请求带上响应里的 token
curl.exe -s "http://127.0.0.1:8080/api/doctors?pn=1&size=5" -H "Authorization: <token>"
```

未登录访问返回 `{"code":10006,"message":"未登录或登录失效"}`。

## 已实现模块

| 任务 | 模块 | 主要接口 |
|---|---|---|
| 1 | 用户模型、登录与权限 | `POST /api/login` |
| 3、4 | 医师 | `GET/POST/PUT/DELETE /api/doctors`、`GET /api/doctors/info`、`PUT /api/doctors/reset/{id}` |
| 5、6 | 医药公司 | `/api/companys` |
| 7、8 | 医药公司政策 | `/api/company_policys` |
| 9、10 | 城市信息 | `/api/citys` |
| 11、12 | 医保政策 | `/api/medical_policys` |
| 13、14 | 销售地点 | `/api/sales` |
| 15、16 | 药品信息 + 图片上传 | `/api/drugs`、`POST /api/base/upload`、静态访问 `/image/**` |
| 17、18 | 必备材料 | `/api/materials` |
| 19 | 用户菜单权限 | `GET /api/permissions?roleName=1` |

## 代码结构

```
src/main/java/com/qst/medical/
├── config/        跨域、Swagger、Jackson、Security、图片静态资源映射
├── controller/    各模块控制器
├── domain/        实体类（继承 SuperDomain 的分页基类）
├── entity/        数据库交互实体
├── filter/        JwtFilter
├── handler/       登录成功/失败、未登录、无权限处理器
├── mapper/        MyBatis 接口
├── model/         返回前端的模型
├── param/         接收前端参数
├── service/       业务层
└── util/          JwtUtils、Msg 统一响应
src/main/resources/
├── application.yml
└── mapper/        各模块 MyBatis XML
```
