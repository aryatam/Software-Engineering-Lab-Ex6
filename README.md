# Energy Manager — Strategy & State Patterns

## 1) Project Overview
این پروژه یک سیستم مدیریت مصرف انرژی برای یک ساختمان اداری است که هزینه‌ی انرژی را بر اساس **تعرفه (Strategy)** و **وضعیت سیستم (State)** محاسبه می‌کند.  
برنامه یک رابط خط فرمان (CLI) دارد و دو نوع نقش کاربر را پشتیبانی می‌کند:
- **Manager**: امکان تغییر تعرفه و وضعیت سیستم، مشاهده و محاسبه هزینه.
- **Occupant**: فقط امکان مشاهده وضعیت و محاسبه هزینه.

---

## 2) Strategy Pattern (Pricing Policies)
**هدف:** جدا کردن منطق محاسبه‌ی هزینه‌ی انرژی از هسته‌ی سیستم تا اضافه کردن سیاست‌های جدید بدون تغییر کد اصلی امکان‌پذیر باشد (اصل Open/Closed).

**مراحل پیاده‌سازی:**
1. تعریف اینترفیس `EnergyPricingStrategy` با متدهای:
  - `double costPerUnit()` → هزینه هر واحد انرژی
  - `double calculate(double units)` → محاسبه هزینه مصرف
2. پیاده‌سازی سه استراتژی:
  - `StandardPricing` (۵۰۰ تومان/واحد)
  - `PeakHoursPricing` (۱۰۰۰ تومان/واحد)
  - `GreenModePricing` (۳۰۰ تومان/واحد)
3. تزریق استراتژی به هسته در کلاس `BuildingEnergySystem`. مدیر می‌تواند با `setPricing(...)` تعرفه جاری را تغییر دهد.

**تحقق الگو:**
- **Context** = `BuildingEnergySystem`
- **Strategy Interface** = `EnergyPricingStrategy`
- **Concrete Strategies** = `StandardPricing`, `PeakHoursPricing`, `GreenModePricing`
- در زمان اجرا، کاربر (مدیر) می‌تواند استراتژی دلخواه را ست کند.

---

## 3) State Pattern (System Modes)
**هدف:** تغییر رفتار سیستم بر اساس وضعیت جاری (Active / Eco / Shutdown) بدون استفاده از شرط‌های پیچیده.

**مراحل پیاده‌سازی:**
1. تعریف اینترفیس `SystemState` با متدهای:
  - `String name()` → نام وضعیت
  - `double consumptionMultiplier()` → ضریب مصرف
2. پیاده‌سازی وضعیت‌ها:
  - `ActiveState` → ضریب ۱.۰
  - `EcoModeState` → ضریب ۰.۴
  - `ShutdownState` → ضریب ۰.۰
3. استفاده در هسته:
  - در `BuildingEnergySystem.calculateCost(units)` ابتدا `units` در ضریب وضعیت ضرب می‌شود، سپس با تعرفه جاری محاسبه می‌شود.
  - مثال: ۱۰ واحد مصرف × ضریب ۰.۴ (Eco) = ۴ واحد مؤثر × تعرفه ۵۰۰ = ۲۰۰۰ تومان.

**تحقق الگو:**
- **Context** = `BuildingEnergySystem`
- **State Interface** = `SystemState`
- **Concrete States** = `ActiveState`, `EcoModeState`, `ShutdownState`
- تغییر وضعیت در زمان اجرا با `setState(...)` انجام می‌شود و رفتار سیستم تغییر می‌کند.

---

## 4) Integration of Strategy + State
کلاس `BuildingEnergySystem` ترکیب هر دو الگو را انجام می‌دهد:
```java
double effectiveUnits = units * state.consumptionMultiplier();
return pricing.calculate(effectiveUnits);
```

 Refactoring Summary (Phase 2)

## 1) Replace Magic Numbers with Constants
اعداد ثابتِ پراکنده برای قیمت‌ها و ضرایب، به ثوابت معنادار در `EnergyConstants` منتقل شدند.  
این کار خوانایی را افزایش داد و تغییر قیمت/ضریب را به یک نقطه واحد محدود کرد.

## 2) Self-Encapsulated Field (pricing/state)
دسترسی به فیلدهای `pricing` و `state` فقط از طریق getter/setter انجام می‌شود.  
این الگو امکان اعتبارسنجی و لاگ/هوک‌های جانبی را در یک مسیر کنترل‌شده فراهم کرد.

## 3) Separate Query from Modifier
محاسبه‌ی هزینه (`calculateCost`) از تغییر حالت سیستم جدا شد و ثبت مصرف در `addConsumption` انجام می‌گیرد.  
این تفکیک، عوارض جانبی ناخواسته را حذف و تست‌پذیری منطق محاسبه را ساده کرد.

## 4) Replace Switch with Polymorphism (Menus)
به‌جای switch/case، از enumهای پلی‌مورفیک `PricingOption` و `StateOption` استفاده شد.  
افزودن گزینه‌های جدید اکنون بدون دست‌زدن به منطق UI انجام می‌شود و منوها خودکار به‌روز می‌گردند.

## 5) Facade
کلاس `EnergyFacade` یک API ساده برای UI فراهم کرد و پیچیدگی هسته را پنهان ساخت.  
UI دیگر مستقیم به `BuildingEnergySystem` وابسته نیست و اتصال/تغییرات آینده کم‌هزینه‌تر است.

## 6) Add New Strategy (HolidayPricing)
یک استراتژی جدید (`HolidayPricing`) افزوده شد و از طریق `PricingOption` در منو ظاهر می‌شود.  
این کار قابلیت توسعه‌پذیری الگوی Strategy را نشان داد بدون تغییر در هسته/UI موجود.

## 7) Extract Class (ConsumptionTracker)
منطق ثبت و گزارش مصرف تجمعی به کلاس مستقل `ConsumptionTracker` منتقل شد.  
این جداسازی، مسئولیت‌ها را روشن و کلاس هسته را بر تمرکز بر State/Strategy نگه داشت.


## GoF Categories

**Creational** — الگوهایی برای «ایجاد شیء» که سازوکار ساخت را از استفاده‌کننده جدا می‌کنند؛ تغییر نحوهٔ ساخت، کلاینت را نمی‌شکند.  
مثال‌ها: `Factory Method`, `Abstract Factory` (همچنین: Builder, Prototype, Singleton).

**Structural** — الگوهایی برای «ترکیب اشیا/کلاس‌ها» و تشکیل ساختارهای بزرگ‌تر با رابط ساده‌تر یا کاراتر.  
مثال‌ها: `Facade`, `Adapter` (همچنین: Composite, Decorator, Proxy, Bridge, Flyweight).

**Behavioral** — الگوهایی برای «رفتار و همکاری اشیا» و توزیع مسئولیت‌های محاسباتی/جریانی.  
مثال‌ها: `Strategy`, `State` (همچنین: Observer, Command, Chain of Responsibility, Visitor, Mediator, Template Method, Iterator).


## Phase 1 Patterns → GoF Category

- `Strategy` → **Behavioral** (تعریف خانواده‌ای از سیاست‌های قیمت‌گذاری و سوییچ زمان اجرا).  
- `State` → **Behavioral** (تغییر رفتار سیستم با حالت‌های Active/Eco/Shutdown بدون ifهای پخش‌وپلا).



## Design Choice & Implementation Sketch

- تغییر **حالت سیستم** ⇒ الگوی `State` مناسب است؛ زیرا رفتار (ضریب مصرف، پیام‌های ورود/خروج) را در کلاس‌های حالت کپسوله می‌کند و تغییر/افزودن حالت جدید بدون دست‌کاری هسته ممکن است.
- تغییر **سیاست محاسبهٔ هزینه** ⇒ الگوی `Strategy` مناسب است؛ چون فرمول قیمت‌گذاری را از هسته جدا می‌کند و اضافه‌کردن تعرفهٔ جدید (مثلاً Holiday) تنها با یک کلاس جدید انجام می‌شود.

### کلاس‌ها و نقش‌ها
- **Context**: `BuildingEnergySystem`  
  - نگه‌داری `SystemState` جاری و `EnergyPricingStrategy` جاری  
  - متد کلیدی:
    ```java
    double calculateCost(double units) {
      double eff = units * state.consumptionMultiplier(); // State
      return pricing.calculate(eff);                      // Strategy
    }
    ```
- **State**: `SystemState` (+ `ActiveState`, `EcoModeState`, `ShutdownState`)  
- **Strategy**: `EnergyPricingStrategy` (+ `Standard`, `PeakHours`, `GreenMode`, `Holiday`)
- تغییرات زمان اجرا با `setState(...)` و `setPricing(...)` انجام می‌شود؛ UI فقط این ست‌ترها را صدا می‌زند.


## SOLID in This Project

**S — Single Responsibility**: هر کلاس تنها یک دلیل برای تغییر دارد؛ مثلاً `ActiveState` فقط منطق حالت Active را دارد و `StandardPricing` فقط قیمت‌گذاری Standard.

**O — Open/Closed**: سیستم برای افزودن تعرفه/حالت جدید «باز» و برای تغییر کد موجود «بسته» است؛ با افزودن کلاس `HolidayPricing` بدون تغییر هسته، قابلیت گسترش داریم.

**L — Liskov Substitution**: هر `EnergyPricingStrategy` یا `SystemState` باید بدون شکستن منطق، جایگزین نوع والد شود؛ کلاینت فقط از رابط استفاده می‌کند.

**I — Interface Segregation**: رابط‌ها کوچک و متمرکزند (`EnergyPricingStrategy`, `SystemState`)، کلاینت‌ها مجبور به پیاده‌سازی متدهای بلااستفاده نیستند.

**D — Dependency Inversion**: `BuildingEnergySystem` به «رابط‌ها» وابسته است نه پیاده‌سازی‌ها؛ با تزریق استراتژی/استیت Concrete، سطح بالا از سطح پایین جدا می‌شود.

## پاسخ پرسش ۵

### 5-1) هر مفهوم در یک خط
- **کد تمیز (Clean Code):** کدی که خوانا و ساده است، نام‌گذاری شفاف و توابع کوتاه دارد، بدون شگفتی عمل می‌کند و به‌راحتی قابل‌تست و تغییر است.  
- **بدهی فنی (Technical Debt):** هزینه‌ی آینده‌ای که به‌خاطر میان‌بُرهای امروز می‌پردازیم؛ یعنی تغییرات سریعِ بی‌قاعده که بعداً با بازآرایی/بازنویسی باید «بازپرداخت» شوند.  
- **بوی بد کد (Code Smell):** نشانه‌ی ظاهریِ ضعف طراحی/نگه‌داری (نه لزوماً باگ) که احتمالاً باید به بازآرایی منجر شود.

---

### 5-2) پنج دسته‌ی بوی بد کد در refactoring.guru (خلاصه)
- **Bloaters:** قطعات بادکرده/غول‌آسا (کلاس‌های بزرگ، متدهای طولانی، لیست پارامترهای زیاد) که فهم و تغییر را سخت می‌کنند.  
- **Object-Orientation Abusers:** سوء‌استفاده از OOP (switch روی نوع، متدهای خدا، داده‌های ساکن به‌جای آبجکت، بی‌توجهی به پلی‌مورفیسم).  
- **Change Preventers:** کدهایی که مانع تغییر سالم‌اند؛ یک تغییر کوچک، چند فایل نامرتبط را درگیر می‌کند (Shotgun Surgery, Divergent Change).  
- **Dispensables:** چیزهای زائد و بی‌ارزش (کد مرده، تکرار، کامنت‌های بی‌فایده، داده‌ی استفاده‌نشده) که باید حذف/ادغام شوند.  
- **Couplers:** وابستگی‌های مضر بین ماژول‌ها (Feature Envy، Inappropriate Intimacy، Message Chains) که انعطاف را کم می‌کند.

---

### 5-3) بوی بد «Feature Envy»
- **در کدام دسته است؟** در گروه **Couplers** (وابستگی‌های مضر) قرار می‌گیرد.  
- **برای برطرف‌کردن چه بازآرایی‌هایی پیشنهاد می‌شود؟** `Move Method` (گاهی `Move Field`)، `Extract Method`/`Extract Class`، در صورت تکرار پارامترها `Introduce Parameter Object`.  
- **چه زمانی می‌توان نادیده‌اش گرفت؟** وقتی کلاس نقش «هماهنگ‌کننده/Service» دارد و ذاتاً باید داده‌ی دیگران را اورکستر کند، یا انتقال متد وابستگی‌های چرخه‌ای/هزینه‌ی زیادی ایجاد می‌کند، یا در Mapper/DTO که ماهیتاً به داده‌ی دیگری سر می‌زند.


## 5) پیدا کردن ۱۰ بوی بد در پروژه‌ی «Convert_UML_to_ANSI_C»

> ریپو: `bigsheykh/Convert_UML_to_ANSI_C` — شواهد هر مورد با لینک/کامیت اشاره شده است.

| # | Smell (Group) | کجا دیده شد | شواهد خلاصه | بازآرایی/پیشنهاد |
|---|---|---|---|---|
| 1 | **God/Long Class** (Bloaters) | `Phase1FileGenerator` | این کلاس ده‌ها مسئولیت تولید کد دارد و حتی چندین TODO «move to CompleteX» داخلش آمده (`generateMethod/Constructor/Destructor/Header...`). | **Extract Class / Move Method**: هر متد تولیدی به کلاسِ مربوطه (`CompleteClass`, `CompleteMethod`, `CompleteConstructor`) منتقل شود؛ این کلاس صرفاً هماهنگ‌کننده بماند. :contentReference[oaicite:0]{index=0} |
| 2 | **Inconsistent/Misspelled Name** (Dispensables) | `Phase1FileGenerator` | فیلد بولی `successFull` (املای نادرست) معرف وضعیت موفقیت است. | **Rename** به `successful` یا `isSuccessful`؛ نام‌گذاری یکدست خوانایی را بالا می‌برد. :contentReference[oaicite:1]{index=1} |
| 3 | **Raw Type Usage** (OO Abusers) | `CompleteClass.modifyByParents(String className, Vector diagram, int counter)` | استفاده از `Vector` خام (بدون جنریک) در امضای متد. | **Use Generics**: امضای متد به `Vector<...>` یا بهتر `List<...>` تغییر کند. :contentReference[oaicite:2]{index=2} |
| 4 | **Obsolete Collection Choice** (Bloaters/Dispensables) | چند فایل (Importِ `java.util.Vector`) | هنوز از `Vector` (synchronized و قدیمی) استفاده می‌شود. | **Prefer List/ArrayList** و فقط در صورت نیاز واقعی از ساختارهای همگام‌شده استفاده کنید. :contentReference[oaicite:3]{index=3} |
| 5 | **Magic String Sentinel** (Dispensables/Change Preventers) | `ClassDiagram` | شرط قدیمی `!tClass.getSuperClass().equals("null")` برای تشخیص نبودِ سوپرکلاس حذف شده و با `havingSuperClass()` جایگزین شده است—استفاده از `"null"` به‌عنوان مقدار نگهبان بود. | استفاده از متد/نوع معنادار (یا `Optional`) به‌جای رشته‌ی جادویی. :contentReference[oaicite:4]{index=4} |
| 6 | **Magic String Sentinel** (Dispensables) | `ClassStructure.getAllProblems()` | همچنان چک `getName().equals("null")` وجود دارد (نامِ کلاس برابر "null"). | **Remove Sentinel**: پرچم/نوع مناسب برای «نداشتن نام» یا اعتبارسنجی ورودی جایگزین شود. :contentReference[oaicite:5]{index=5} |
| 7 | **Copy-pasted/Irrelevant Comment** (Dispensables) | `GUIValueType` | کامنت جاواداک «set task limit for downloads» بی‌ربط به کد GUI و حذف شده است. | **Remove Comment** و مستندسازی هدفمند. :contentReference[oaicite:6]{index=6} |
| 8 | **TODO Debt in Production** (Dispensables) | `Phase1FileGenerator` | TODOهای متعدد «move to … / use StringBuilder» وسط منطق تولید کد مانده‌اند. | **Create Issues & Refactor**: TODOها به Issue Tracker منتقل و در چند PR با Extract/Move Method انجام شود. :contentReference[oaicite:7]{index=7} |
| 9 | **Leaky Abstraction / Inappropriate Intimacy** (Couplers) | `LexicalAnalyzer` ↔ `DependencyEdge` | برای مصرف در `DependencyEdge`، متدهای `deleteClassSpecifier` و `unStruct` از `private` به package-private تغییر داده شده‌اند؛ استفاده مستقیم از توابع داخلی لکسیکال. | **Introduce Utility API**: یک کلاس Utility پابلیکِ پایدار (مثلاً `CNameUtils`) با تست ایجاد و فقط همان API مصرف شود. :contentReference[oaicite:8]{index=8} |
|10| **Duplicated Logic** (Dispensables/Change Preventers) | `ClassDiagram` | منطق مشابه بررسیِ برخورد نام صفت/متد با نام کلاس، هم در `statusOfMember` و هم در `getAllProblems` تکرار شده است. | **Extract Method**: یک متد/ولیداتور مشترک برای این چک‌ها ساخته شود تا منبع واحدِ حقیقت داشته باشیم. :contentReference[oaicite:9]{index=9}




## 6) Formatter Plugin چیست و چرا مفید است؟

**چیست؟**  
یک ابزار خودکار برای یکسان‌سازی *استایل* کد (تورفتگی، فاصله‌ها، براکت‌ها، طول خطوط، ایمپورت‌ها…). در جاوا معمولاً از
`spotless-maven-plugin` با موتور `google-java-format` استفاده می‌کنیم.  

**چه می‌کند؟**  
- با `mvn spotless:apply` کد را طبق قانون تعیین‌شده *بازقالب‌بندی* می‌کند.  
- با `mvn spotless:check` در CI بررسی می‌کند که فایل‌ها مطابق قالب هستند و در صورت عدم تطابق بیلد را fail می‌کند.  
- هیچ تغییری در رفتار برنامه ایجاد نمی‌کند (فقط ظاهر کد عوض می‌شود).

**چرا کمک‌کننده است؟**  
- **Diffهای کوچک‌تر و خواناتر:** چون استایل یکسان می‌شود، در Pull Request فقط *تغییرات معنایی* باقی می‌ماند، نه اختلاف فاصله/براکت.  
- **کاهش بحث‌های سلیقه‌ای:** تیم به‌جای «بحث استایل»، روی «طراحی و منطق» تمرکز می‌کند.  
- **بازبینی و نگه‌داری سریع‌تر:** خوانایی یکنواخت باعث می‌شود smellها و قطعات مشکل‌دار راحت‌تر دیده شوند.  
- **استانداردسازی در CI:** تضمین می‌کند همه با یک فرمت مشترک کامیت می‌کنند.

**رابطه با بازآرایی کد چیست؟**  
- **خودِ فرمت کردن، Refactor نیست** (رفتار را عوض نمی‌کند)، اما **زمینه‌ساز بازآرایی سالم** است:  
  1) نویز بصری را حذف می‌کند تا هنگام بازآرایی، تغییرات ساختاری به‌وضوح دیده شوند.  
  2) امکان تفکیک کامیت‌ها: «Commit 1 = فقط format»، «Commit 2 = refactor» ⇒ بررسی/بازگشت ساده‌تر.  
  3) با خوانایی بهتر، شناسایی smellها (Long Method/Duplicated Code/…) آسان‌تر می‌شود.

**نمونه تنظیم در `pom.xml`**  
```xml
<plugin>
  <groupId>com.diffplug.spotless</groupId>
  <artifactId>spotless-maven-plugin</artifactId>
  <version>2.43.0</version>
  <configuration>
    <java>
      <googleJavaFormat/>
    </java>
  </configuration>
</plugin>
