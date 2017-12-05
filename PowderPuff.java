/*
 - Check to make sure move around ball works correctly : CLAIRE
 - checked off - Assign players to positions : MICHELLE
 - Get other people to move : CLAIRE
 - Finish leftForward and rightForward functions : MICHELLE
 - Defender function : MICHELLE
 
 
 - Have left forward and right forward run towards the goal until they're ahead
 of attack player
 - If a defender is in front of you, the attacker, try to pass to a forward. If
 not, just keep going towards the goal
 */


import java.awt.*;

public class PowderPuff extends Player {
    
    
    static int playersX[];
    static int playersY[];
    static int distancesToBall[];
    static boolean someoneOnBall;
    static int playersOnBall[];
    static final int ATTACK = 1;
    static int attacker = 0;
    static final int LEFTFORWARD = 2;
    static int leftForward = 1;
    static final int RIGHTFORWARD = 3;
    static int rightForward = 2;
    static final int DEFENDER = 4;
    static int defender = 3;
    static final int playerDistance = 8;
    static int positions[];
    
    /* Check to see if there is a "clear" path by checking the direction around
     each player */
    public boolean isClearPath(int x1, int y1, int x2, int y2) {
        int xDifference = Math.abs(x1 - x2);
        int yDifference = Math.abs(y1 - y2);
        int startX, endX;
        int startY, endY;
        boolean isClear = false;
        
        if (y1 < y2) {
            if (Look(EAST) == EMPTY) {
                isClear = true;
            }
        } else {
            if (Look(WEST) == EMPTY) {
                isClear = true;
            }
        }
        if (x1 < x2) {
            startX = x1;
            endX = x2;
            if (Look(EAST) == EMPTY) {
                isClear = true;
            }
        } else {
            startX = x2;
            endX = x1;
            if (Look(WEST) == EMPTY) {
                isClear = true;
            }
        }
        return isClear;
    }
    public int MoveToClearPath(int position) {
        int attackX = 0;
        int attackY = 0;
        int secondX = 0;
        int secondY = 0;
        for (int i = 0; i <= 3; i++) {
            if (positions[i] == ATTACK) {
                attackX = playersX[i];
                attackY = playersY[i];
            } else if (positions[i] == LEFTFORWARD && positions[i] == position) {
                secondX = playersX[i];
                secondY = playersY[i];
            } else if (positions[i] == RIGHTFORWARD && positions[i] == position) {
                secondX = playersX[i];
                secondY = playersY[i];
            } else if (positions[i] == DEFENDER && positions[i] == position) {
                secondX = playersX[i];
                secondY = playersY[i];
            }
        }
        if (isClearPath(attackX, attackY, secondX, secondY)) {
            return KICK;
        } else if (isClearPath(attackX, attackY, secondX + 1, secondY)) {
            return EAST;
        } else if (isClearPath(attackX, attackY, secondX - 1, secondY)) {
            return WEST;
        } else if (isClearPath(attackX, attackY, secondX, secondY - 1)) {
            return NORTH;
        } else if (isClearPath(attackX, attackY, secondX, secondY + 1)) {
            return SOUTH;
        } else if (isClearPath(attackX, attackY, secondX + 1, secondY + 1)) {
            return SOUTHEAST;
        } else if (isClearPath(attackX, attackY, secondX - 1, secondY + 1)) {
            return SOUTHWEST;
        } else if (isClearPath(attackX, attackY, secondX + 1, secondY - 1)) {
            return NORTHEAST;
        } else if (isClearPath(attackX, attackY, secondX - 1, secondY - 1)) {
            return NORTHWEST;
        } else {
            return WEST;
        }
    }
    
    public int KickToClearPath() {
        int move = PLAYER;
        int attackX = 0;
        int attackY = 0;
        int forwardX = 0;
        int forwardY = 0;
        //        int kickTo = 0;
        //
        //        for (int i = 7; i >= 0; i++) {
        //            if (i != EAST && Look(i) == EMPTY) {
        //                kickTo = i;
        //            }
        //        }
        for (int i = 0; i <= 3; i++) {
            if (positions[i] == ATTACK) {
                attackX = playersX[i];
                attackY = playersY[i];
            }
            if (positions[i] == LEFTFORWARD || positions[i] == RIGHTFORWARD) {
                forwardX = playersX[i];
                forwardY = playersY[i];
            }
        }
        if (isClearPath(attackX, attackY, forwardX, forwardY)) {
            //move around ball to kick in the correct direction
            move = MoveAroundBall();
        }
        return move;
    }
    
    //attack function
    
    //Goals: move the ball west
    // defend if necessary
    // kick away from majority of players
    
    public int Attack() {
        int move = GetBallDirection();
        int x = GetLocation().x;
        int index = 0;
        for (int i = 0; i <= 3; i++) {
            if (positions[i] == ATTACK) {
                index = i;
            }
        }
        
        if (playersOnBall[index] == 1) {
            //            if (Look(WEST) != EMPTY) {
            //                return KickToClearPath();
            //            } else {
            //                return MoveAroundBall();
            //            }
            
            //move to ball
            /*move = GetBallDirection();
             for (int i = 0; i <= 7; i++) {
             if(Look(i) == BALL) {
             move = MoveAroundBall();
             }
             }*/
            if (Look(NORTH) == BALL) {
                //check for opponents
                if (Look(WEST) == OPPONENT || Look(NORTHWEST) == OPPONENT) {
                    return KICK;
                }
                //get ball away from goal if there are also opponents
                else if ((x > 3 * FieldX() / 4) && Look(WEST) == OPPONENT || Look(NORTHWEST) == OPPONENT) {
                    return KICK;
                }
                //if no opponents and not near goal move northeast so can kick west
                else {
                    return NORTHEAST;
                }
                
            }
            
            if (Look(NORTHWEST) == BALL) {
                //if close to goal, kick ic
                if ((x > 3 * FieldX() / 4)) {
                    return KICK;
                }
                
                //if not near goal move north so can kick west
                else {
                    return NORTH;
                }
                
            }
            if (Look(WEST) == BALL) {
                return KICK;
            }
            
            if (Look(SOUTHWEST) == BALL) {
                if ((x > 3 * FieldX() / 4)) {
                    return KICK;
                }
                
                //if not near goal move nouth so can kick west
                else {
                    return SOUTH;
                }
                
            }
            if (Look(SOUTH) == BALL) {
                //check for opponents
                if (Look(WEST) == OPPONENT || Look(SOUTHWEST) == OPPONENT) {
                    return KICK;
                }
                //get ball away from goal if there are also opponents
                else if ((x > 3 * FieldX() / 4) && (Look(WEST) == OPPONENT || Look(NORTHWEST) == OPPONENT)) {
                    return KICK;
                }
                //if no opponents and not near goal move northeast so can kick west
                else {
                    return SOUTHEAST;
                }
                
            }
            if (Look(SOUTHEAST) == BALL) {
                //try to get into position to kick ball
                return EAST;
            }
            if (Look(EAST) == BALL) {
                if (Look(NORTHEAST) == EMPTY) {
                    return NORTH;
                }
                if (Look(SOUTHEAST) == EMPTY) {
                    return SOUTH;
                }
                if (Look(WEST) == OPPONENT) {
                    return WEST;
                }
                if (Look(SOUTH) == EMPTY) {
                    return SOUTH;
                }
                return NORTH;
                
            }
            if (Look(NORTHEAST) == BALL) {
                return EAST;
                
            }
        }
        return GetBallDirection();
    }

    //leftForward function 
    public int SouthForward() {
        int horizontal = -1;
        int vertical = -1;
        int x = GetLocation().x;
        int y = GetLocation().y;
        
        for (int i = 0; i <= 3; i++) {
            if (positions[i] == ATTACK) {
                attacker = i;
            }
            if (positions[i] == LEFTFORWARD) {
                leftForward = i;
            }
        }

        /* If near the ball, && on defense, act like a defender */
        if (GetBallDistance() < 2 && (x > 3 * FieldX() / 4)) {
            return (defender());
        /* If near the ball, && on offense, act like a leader */
        } else if (GetBallDistance() < 2 && (x < (FieldX()/2))) {

            assignAttacker();
            return(Attack());
        }


        //get out of way of attacker
        /* if (isClearPath(playersX[attacker], playersY[attacker],
         playersX[leftForward], playersY[leftForward])) {
         return MoveToClearPath(LEFTFORWARD);
         }*/
        
        /* Try to get into position */
        
        //no on is north and attacker insn't in your way
        if (Look(NORTH) == EMPTY && (y > playersY[attacker] - playerDistance)) {
            vertical = NORTH;
        }
        if (Look(SOUTH) == EMPTY && (y < playersY[attacker] - playerDistance)) {
            vertical = SOUTH;
        }
        
        //if behind attacker but in same y line, move down
        if ((x < playersX[attacker]) && (y == playersY[attacker])) {
            vertical = NORTH;
        }
        if (Look(WEST) == EMPTY && (x > playersX[attacker] + playerDistance)) {
            horizontal = WEST;
        }
        if (Look(EAST) == EMPTY && (x < playersX[attacker] + playerDistance)) {
            horizontal = EAST;
        }
        
        if ((horizontal == EAST) && (vertical == NORTH)) {
            return(NORTHEAST);
        }
        if ((horizontal == EAST) && (vertical == SOUTH)) {
            return(SOUTHEAST);
        }
        if ((horizontal == WEST) && (vertical == NORTH)) {
            return(NORTHWEST);
        }
        if ((horizontal == WEST) && (vertical== SOUTH)) {
            return(SOUTHWEST);
        }
        if (horizontal == EAST) {
            return(EAST);
        }
        if (horizontal == WEST) {
            return(WEST);
        }
        if (vertical == NORTH) {
            return(NORTH);
        }
        if (vertical == SOUTH) {
            return(SOUTH);
        }
        return GetBallDirection();
    }

    //rightForward function -- acts like defender that stays on 
    //north side of field, so move and then defend
    public int NorthForward() {
      int move = WEST;
      //find attack index
      int attacker = 0;
      //find rightForward index
      int rightForward = 0;
        //get into position
        //if you have ball & defense in front of you, pass to right forward
        //else keep moving toward ball down the field
        int horizontal = -1;
        int vertical = -1;
        int x = GetLocation().x;
        int y = GetLocation().y;
        
        for (int i = 0; i <= 3; i++) {
            if (positions[i] == ATTACK) {
                attacker = i;
            }
            if (positions[i] == RIGHTFORWARD) {
                rightForward = i;
            }
        }
        /* If near the ball, act like a leader */
        if (GetBallDistance() < 2) {
            assignAttacker();
            return(Attack());
        }
        /*if (isClearPath(playersX[attacker], playersY[attacker],
         playersX[rightForward], playersY[rightForward])) {
         return MoveToClearPath(RIGHTFORWARD);
         }*/
        
        //if in back half of field, act like on defense
        if ((x > 3 * FieldX() / 4)) {
            return (defender());
        }
        /* Try to get into position */
        
        //no on is north and attacker insn't in your way
        if (Look(NORTH) == EMPTY && (y > playersY[attacker] + playerDistance) ) {
            vertical = NORTH;
        }
        if (Look(SOUTH) == EMPTY && (y < playersY[attacker] + playerDistance) ) {
            vertical = SOUTH;
        }
        
        //if behind attacker but in same y line, move down
        if ((x < playersX[attacker]) && (y == playersY[attacker])) {
            vertical = SOUTH;
        }
        if (Look(WEST) == EMPTY && (x > playersX[attacker] + playerDistance)) {
            horizontal = WEST;
        }
        if (Look(EAST) == EMPTY && (x < playersX[attacker] + playerDistance)) {
            horizontal = EAST;
        }
        
        if ((horizontal == EAST) && (vertical == NORTH)) {
            return(NORTHEAST);
        }
        if ((horizontal == EAST) && (vertical == SOUTH)) {
            return(SOUTHEAST);
        }
        if ((horizontal == WEST) && (vertical == NORTH)) {
            return(NORTHWEST);
        }
        if ((horizontal == WEST) && (vertical== SOUTH)) {
            return(SOUTHWEST);
        }
        if (horizontal == EAST) {
            return(EAST);
        }
        if (horizontal == WEST) {
            return(WEST);
        }
        if (vertical == NORTH) {
            return(NORTH);
        }
        if (vertical == SOUTH) {
            return(SOUTH);
        }
        return GetBallDirection();
    }
    
    //defender function
    public int defender() {
        int x = GetLocation().x;
        int y = GetLocation().y;
        
        //priority 1 = get ball out of the goal zone
        //priority 2 = get between defender and goal
        
        if (Look(SOUTHWEST) == BALL) {
            if (Look(SOUTH) == EMPTY) {
                return NORTH;
            }
            return KICK;
        }
        
        else if (Look(NORTHWEST) == BALL) {
            if (Look(NORTH) == EMPTY) {
                return NORTH;
            }
            return KICK;
        }
        
        else if (Look(WEST) == BALL) {
            //kick ball away
            return KICK;
        }
        //try to get between the ball and the endline
        else if (Look(NORTH) == BALL) {
            /* If an opponent can kick toward my goal, try to kick the ball
             away */
            if (Look(WEST) == OPPONENT && Look(NORTHWEST) == OPPONENT) {
                return KICK;
            }
            
            if (Look(NORTHEAST) != EMPTY && Look(EAST) != EMPTY) {
                return KICK;
            }
            if (Look(NORTHEAST) == EMPTY) {
                return NORTHEAST;
            }
            return EAST;
            
        }
        else if (Look(EAST) == BALL) {
            if (Look(NORTHEAST) == EMPTY && Look(WEST) != OPPONENT) {
                return NORTHEAST;
            }
            if (Look(SOUTHEAST) == EMPTY && Look(WEST) != OPPONENT) {
                return SOUTHEAST;
            }
            //can we do nothing?
            return EAST;
        }
        
        else if (Look(SOUTH) == BALL) {
            /* If an opponent can kick toward my goal, try to kick the ball
             away */
            if (Look(WEST) == OPPONENT && (Look(SOUTHWEST) == OPPONENT)) {
                return(KICK);
            }
            return SOUTHEAST;
        }
        
        else if (Look(SOUTHEAST) == BALL || Look(SOUTHEAST) == OPPONENT) {
            //get between ball and goal
            
            /** east or west? **/
            return(EAST);
        }
        else if (Look(NORTHEAST) == BALL || Look(NORTHEAST) == OPPONENT) {
            return EAST;
        }
        else if (Look(NORTH) == OPPONENT) {
            if (Look(NORTHEAST) == EMPTY) {
                return NORTHEAST;
            }
            return EAST;
            
        }
        else if (Look(SOUTH) == OPPONENT) {
            if (Look(SOUTHEAST) == EMPTY) {
                return SOUTHEAST;
            }
            return EAST;
            
        }
        else if (Look(EAST) == OPPONENT) {
            if (Look(NORTHEAST) == EMPTY) {
                return NORTHEAST;
            }
            if (Look(SOUTHEAST) == EMPTY) {
                return SOUTHEAST;
            }
            
        }
        else if (Look(WEST) == OPPONENT) {
            return WEST;
        }
        else if (Look(NORTHWEST) == OPPONENT) {
            return NORTH;
        }
        else if (Look(SOUTHWEST) == OPPONENT) {
            return SOUTH;
        }
        
        //if no ball or opponents around, act like attacker
        return GetBallDirection() ;
    }
    
    
    public void assignAttacker() {
        attacker = GetPlayerClosestToBall();
        
        assignOthers(attacker);
    }
    
    //assignOthers function
    public void assignOthers(int attacker) {
        int lowX = Integer.MAX_VALUE;
        int highX = Integer.MIN_VALUE;
        int lowY = Integer.MAX_VALUE;
        int highY = Integer.MIN_VALUE;
        int newLeftForward = 0;
        int newRightForward = 0;
        int newDefender = 0;
        for (int i = 0; i < 4; i++) {
            positions[i] = 0;
        }
        
        positions[attacker] = ATTACK;
        
        //assigning defender
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                if (playersX[i] > highX) {
                    highX = playersX[i];
                    newDefender = i;
                }
            }
        }
        defender = newDefender;
        positions[defender] = DEFENDER;
        
        //assigning leftForward
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                if (playersY[i] > highY) {
                    lowY = playersY[i];
                    newLeftForward = i;
                }
            }
        }
        leftForward = newLeftForward;
        positions[leftForward] = LEFTFORWARD;
        
        //assigning rightForward
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                if (playersY[i] < lowY) {
                    highY = playersY[i];
                    newRightForward = i;
                }
            }
        }
        rightForward = newRightForward;
        positions[rightForward] = RIGHTFORWARD;
        
        
    }
    
    public void InitializeGame () {
        playersX = new int[4];
        playersY = new int[4];
        distancesToBall = new int[4];
        someoneOnBall = false;
        playersOnBall = new int[4];
        positions = new int[4];
    }
    
    public void InitializePoint() {
        for (int i = 0; i <= 3; i++) {
            playersX[i] = 0;
            playersY[i] = 0;
            playersOnBall[i] = 0;
        }
        positions[0] = ATTACK;
        positions[1] = RIGHTFORWARD;
        positions[2] = LEFTFORWARD;
        positions[3] = DEFENDER;
    }
    
    public int AlternateMove(int move) {
        int originalMove = move;
        move = move + 1;
        while (Look(move) != EMPTY || Look(move) == BOUNDARY) {
            // try another move
            if (move < NORTHWEST) {
                move++;
            } else {
                move = 0;
            }
            if (move == originalMove) {
                return PLAYER;
            }
        }
        return move;
    }
    public int GetPlayerClosestToBall() {
        int minDistance = distancesToBall[0];
        int closestPlayer = 0;
        for (int i = 1; i <= 3; i++) {
            if (distancesToBall[i] < minDistance) {
                minDistance = distancesToBall[i];
                closestPlayer = i;
                if (distancesToBall[i] <= 1) {
                    someoneOnBall = true;
                    playersOnBall[i] = 1;
                }
            }
        }
        return closestPlayer;
    }
    
    public int MoveAroundBall() {
        int move = PLAYER;
        int alternateMove = PLAYER;
        int direction = PLAYER;
        for (int i = 0; i <= 7; i++) {
            if (Look(i) == BALL) {
                direction = i;
            }
        }
        switch (direction) {
            case NORTH:
                move = NORTHEAST;
                alternateMove = EAST;
            case NORTHEAST:
                move = EAST;
                alternateMove = NORTH;
            case EAST:
                move = NORTHEAST;
                alternateMove = SOUTHEAST;
            case SOUTHEAST:
                move = EAST;
                alternateMove = SOUTH;
            case SOUTH:
                move = SOUTHEAST;
                alternateMove = EAST;
            case SOUTHWEST:
                move = SOUTH;
                alternateMove = WEST;
            case WEST:
                move = KICK;
                alternateMove = PLAYER;
            case NORTHWEST:
                move = NORTH;
                alternateMove = WEST;
        }
        /* If there is an opponent blocking the move you want to make */
        if (Look(move) != EMPTY || Look(move) == BOUNDARY) {
            move = alternateMove;
        }
        return move;
    }
    
    public int Player1() {
        int action = WEST;
        
        playersX[0] = GetLocation().x;
        playersY[0] = GetLocation().y;
        
        distancesToBall[0] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();
        assignAttacker();
        switch (positions[0]) {
            case ATTACK: action =  Attack();
                break;
            case LEFTFORWARD: action =  SouthForward();
                break;
            case RIGHTFORWARD: action =  NorthForward();
                break;
            case DEFENDER: action =  defender();
                break;
        }
        /*if (playersOnBall[0] == 1) {
         assignAttacker();
         return MoveAroundBall();
         }
         if (Look(GetBallDirection()) == OPPONENT) {
         return AlternateMove(GetBallDirection());
         } else {
         return GetBallDirection();
         }*/
        
        return action;
    }
    
    public int Player2() {
        int action = WEST;
        
        playersX[1] = GetLocation().x;
        playersY[1] = GetLocation().y;
        
        distancesToBall[1] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();
        // assignAttacker();
        switch (positions[1]) {
            case ATTACK: action =  Attack();
                break;
            case LEFTFORWARD: action =  SouthForward();
                break;
            case RIGHTFORWARD: action =  NorthForward();
                break;
            case DEFENDER: action =  defender();
                break;
        }
        /*if (playersOnBall[1] == 1) {
         assignAttacker();
         return MoveAroundBall();
         }
         if (Look(GetBallDirection()) == OPPONENT) {
         return AlternateMove(GetBallDirection());
         } else {
         return GetBallDirection();
         }*/
        
        return action;
    }
    
    public int Player3() {
        int action = WEST;
        
        playersX[2] = GetLocation().x;
        playersY[2] = GetLocation().y;
        
        distancesToBall[2] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();
        //  assignAttacker();
        switch (positions[2]) {
            case ATTACK: action =  Attack();
                break;
            case LEFTFORWARD: action =  SouthForward();
                break;
            case RIGHTFORWARD: action =  NorthForward();
                break;
            case DEFENDER: action =  defender();
                break;
        }
        /*if (playersOnBall[2] == 1) {
         assignAttacker();
         return MoveAroundBall();
         }
         if (Look(GetBallDirection()) == OPPONENT) {
         return AlternateMove(GetBallDirection());
         } else {
         return GetBallDirection();
         }*/
        
        return action;
    }
    
    public int Player4() {
        int action = WEST;
        
        playersX[3] = GetLocation().x;
        playersY[3] = GetLocation().y;
        
        distancesToBall[3] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();
        switch (positions[3]) {
            case ATTACK: action =  Attack();
                break;
            case LEFTFORWARD: action =  SouthForward();
                break;
            case RIGHTFORWARD: action =  NorthForward();
                break;
            case DEFENDER: action =  defender();
                break;
        }
        /* if (playersOnBall[3] == 1) {
         assignAttacker();
         return MoveAroundBall();
         }
         if (Look(GetBallDirection()) == OPPONENT) {
         return AlternateMove(GetBallDirection());
         } else {
         return GetBallDirection();
         }*/
        
        return action;
    }
    
    public void WonPoint () {};
    public void LostPoint () {};
    public void GameOver () {};
    
    
    public int getAction() {
        switch(ID)
        {
            case 1:
                return Player1();
            case 2:
                return Player2();
            case 3:
                return Player3();
            case 4:
                return Player4();
        }
        return BALL;
    }
}
