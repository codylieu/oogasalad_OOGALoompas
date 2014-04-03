package main.java.engine.objects.monster;

import java.awt.geom.Point2D;

/**
 * Move monster toward exit in straight line path.
 * May be used for flying enemies.
 * @author Austin
 *
 */
public class StraightLinePath implements IMonsterPath {

    private Point2D myTargetExit;
    private Monster myMonster;

    public StraightLinePath (Monster monsterToControl, Point2D destination) {
        myMonster = monsterToControl;
        myTargetExit  = destination;
    }

    // will need access to knowledge of exit(s), towers and terrain obstacles.
    @Override
    public void navigateMonster () {
        if (myMonster.x > myTargetExit.getX()) {
            myMonster.x--;
        }
        else {
            myMonster.x++;
        }

        if (myMonster.y > myTargetExit.getY()) {
            myMonster.y--;
        }
        else {
            myMonster.y++;
        }
    }

}
