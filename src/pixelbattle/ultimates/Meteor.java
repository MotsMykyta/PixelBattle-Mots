package pixelbattle.ultimates;
import pixelbattle.core.Fighter;
import pixelbattle.core.Ultimate;
/*
 * 8: ультимейт «Метеорит» — ЗВИЧАЙНИЙ (іменований) клас:
 *
 *   public class Meteor implements Ultimate { ... }
 *
 *   - getName()        → "Метеорит"
 *   - getDescription() → короткий опис
 *   - activate(...)    → кожному живому ворогу Fighter.random(20, 30) шкоди;
 *                        поверніть рядок для журналу (наприклад, загальну шкоду).
 *
 *   Потрібні імпорти: pixelbattle.core.Fighter, pixelbattle.core.Ultimate.
 *   Зверніть увагу: Meteor НЕ є бійцем, тому random(...) тут викликається
 *   через ім'я класу: Fighter.random(20, 30).
 *
 *   Потім підключіть ультимейт у конструкторі Mage: setUltimate(new Meteor());
 */
public class Meteor implements Ultimate{
    @Override
    public String getName(){
        return "Метеор";
    }

    @Override
    public String getDescription() {
        return "Завдає 20-30 шкоди кожному живому ворогу";
    }

    @Override
    public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
        int totalDamage = 0;
        for (Fighter e : enemies){
            if(e.isAlive()){
                totalDamage += e.takeDamage(Fighter.random(20, 30));
            }
        }
        return user.getName() + " обрушує Метеорит на всіх ворогів (загальна шкода: " + totalDamage + ") ";
    }

}
