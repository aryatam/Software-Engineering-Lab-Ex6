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
