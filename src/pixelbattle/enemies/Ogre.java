package pixelbattle.enemies;

import pixelbattle.core.*;
import pixelbattle.engine.Sprite;

/** Огр — бос. Повільний, але дуже сильний. */
public class Ogre extends Enemy {

    public Ogre() {
        super("Огр", 200, new Sprite("ogre_idle_anim", 4, 4));

        addSkill(new Skill("Удар кийком", Target.ENEMY, 0, (user, target) -> {
            int dmg = random(14, 20);
            if (user.getHp() < user.getMaxHp() / 2) dmg += 8;    // лють
            return user.getName() + " б'є кийком " + target.getName() + ": "
                    + target.takeDamage(dmg) + " шкоди";
        }));

        //   10: ультимейт «Землетрус» — АНОНІМНИЙ КЛАС:
        //   кожному живому герою 12 шкоди, а сам огр лікується на 20.
        //   Увага: для огра вороги — це герої, тобто масив enemies у activate(...).
        //   Огр використає ультимейт сам, щойно енергія заповниться.
        setUltimate(new Ultimate() {
            @Override
            public String getName() {
                return "Землетрус";
            }

            @Override
            public String getDescription() {
                return "Завдає кожному живому герою 12 шкоди, а сам огр лікується на 20.";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                for(Fighter hero : enemies){
                    if(hero.isAlive()){
                        hero.takeDamage(12);
                    }
                }
                return user.getName() + "викликає Землетрус (12 шкоди всім героям) і відновлює "
                        + user.heal(20) + " HP!";
            }
        });
    }

    // ПРИКЛАД ПАСИВКИ: «Товста шкіра» — за один удар огр втрачає не більше 25 HP.
    @Override
    protected int modifyIncomingDamage(int damage) {
        return Math.min(damage, 25);
    }
}
