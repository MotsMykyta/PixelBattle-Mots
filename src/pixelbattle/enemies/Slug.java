package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.engine.Sprite;

public class Slug extends Enemy {
    public Slug(){
        super("Слизняк", 45, new Sprite("slug_anim", 4, 6));
        addSkill(new Skill("Кислотний плювок", Target.ENEMY, 0,(user, target) ->
                user.getName() + " плює кислотою в " + target.getName() + ": "
                        + target.takeDamage(random(4, 7)) + " шкоди" ));
        addSkill(new Skill("Лікувальний слиз", Target.ALLY, 2, (user, target) ->
                user.getName() + " підліковує " + target.getName() +
                        " на " + target.heal(12) + " HP"));
    }
    @Override
    protected int modifyIncomingDamage(int damage){
        if(chance(30)){
            return 0;
        }
        return damage;
    }
}
