package pixelbattle.heroes;

import pixelbattle.core.*;
import pixelbattle.engine.Sprite;

/** Лучниця — швидка й спритна. */
public class Archer extends Hero {

    public Archer() {
        super("Лучниця", 80, new Sprite("elf_f_idle_anim", 4, 4));

        addSkill(new Skill("Постріл", Target.ENEMY, 0, (user, target) ->
                user.getName() + " стріляє в " + target.getName() + ": "
                        + target.takeDamage(random(10, 16)) + " шкоди"));


        //  3: вміння «Подвійний постріл» (Target.ENEMY, перезарядка 1) ЛЯМБДОЮ:
        //   два постріли по random(7, 11); у журнал — сума шкоди.
        addSkill(new Skill("Подвійний постріл", Target.ENEMY, 1, (user, target) -> {
               return user.getName() + " робить 1-ий постріл в " + target.getName() + " на " + target.takeDamage(random(7, 11))
                       + " шкоди" + " робить 2-ий постріл в " + target.getName() + " на: "
                       + target.takeDamage(random(7, 11)) + " шкоди";
        }));

        //   9: ультимейт «Злива стріл» — АНОНІМНИЙ КЛАС з полем-лічильником:
        //   кожен живий ворог отримує N стріл по random(4, 8) шкоди,
        //   де N = 3 при першому використанні, 4 — при другому, 5 — при третьому...
        //   (лічильник — поле всередині анонімного класу; знадобиться вкладений цикл).
        setUltimate(new Ultimate() {
            private int arrows = 3;
            @Override
            public String getName() {
                return "Злива стріл";
            }

            @Override
            public String getDescription() {
                return "Випускає " + arrows + " стріл по 4-8 шкоди у кожного живого ворога " +
                        "(кількість зростає з кожним використанням)";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int totalDamage = 0;
                int count = arrows;
                arrows++;
                for(Fighter e : enemies){
                    if(e.isAlive()){
                        for(int i = 0; i < count; i++){
                            if(e.isAlive()){
                                totalDamage += e.takeDamage(random(4,8));
                            }
                        }
                    }
                }
                return user.getName() + " викликає Зливу стріл (зі " + count + " стріл) " +
                        "у кожного живого ворога (загальна шкода " + totalDamage + ") " ;
            }
        });

    }

    @Override
    protected int modifyIncomingDamage(int damage){
        if(chance(25)){
            return 0;
        }
        return damage;
    }

    // 5: пасивка «Ухилення» — перевизначте modifyIncomingDamage:
    //   з імовірністю 25% (chance(25)) шкода дорівнює 0, інакше — без змін.
}
