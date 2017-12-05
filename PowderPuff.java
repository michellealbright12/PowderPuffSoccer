/*
- Check to make sure move around ball works correctly : MICHELLE
- checked off - Assign players to positions : MICHELLE
- Get other people to move : CLAIRE
- Finish leftForward and rightForward functions : CLAIRE
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
        int index = 0;
        for (int i = 0; i <= 3; i++) {
            if (positions[i] == ATTACK) {
                index = i;
            }
        }
        if (playersOnBall[index] == 1) {
            return KickToClearPath();
        }
        //move to ball
        move = GetBallDirection();
        for (int i = 0; i <= 7; i++) {
            if(Look(i) == BALL) {
               move = MoveAroundBall();
            }
        }
        return move;
    }

    //leftForward function
    public int leftForward() {
        
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
        if (isClearPath(playersX[attacker], playersY[attacker],
                        playersX[leftForward], playersY[leftForward])) {
            return MoveToClearPath(LEFTFORWARD);
        }

        
        /* If near the ball, act like a leader */
        if (GetBallDistance() < 2) {
            return(Attack());
        }
        
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
        return(GetBallDirection());
        
        //if you have ball & defense in front of you, pass to right forward


    }

    //rightForward function
    public int rightForward() {
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
        if (isClearPath(playersX[attacker], playersY[attacker],
                        playersX[rightForward], playersY[rightForward])) {
            return MoveToClearPath(RIGHTFORWARD);
        }
        
        
        /* If near the ball, act like a leader */
        if (GetBallDistance() < 2) {
            return(Attack());
        }
        
        /* Try to get into position */
        
        //no on is north and attacker insn't in your way
        if (Look(NORTH) == EMPTY && (y > playersY[attacker] + playerDistance)) {
            vertical = NORTH;
        }
        if (Look(SOUTH) == EMPTY && (y < playersY[attacker] + playerDistance)) {
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
        return(GetBallDirection());
    }

    //defender function
    public int defender() {
        int horizontal = -1;
        int vertical = -1;
        int x = GetLocation().x;
        int y = GetLocation().y;
        
        
        if (Look(SOUTHWEST) == BALL) {
        }
        
        if (Look(NORTH) == BALL) {
        }
        
        if (Look(WEST) == BALL) {
        }
        //try to get between the ball and the endline
        if (Look(NORTH) == BALL) {
            /* If an opponent can kick toward my goal, try to kick the ball
             away */
            if (Look(WEST) == OPPONENT && Look(NORTHWEST) == OPPONENT) {
                return KICK;
            }

        }
        if (Look(EAST) == BALL) {
            
        }
        
        if (Look(SOUTHEAST) == BALL) {
            //get between ball and goal
            
            /** east or west? **/
            return(EAST);
        }
        
        if (Look(SOUTH) == BALL) {
            /* If an opponent can kick toward my goal, try to kick the ball
             away */
            if (Look(WEST) == OPPONENT && (Look(SOUTHWEST) == OPPONENT)) {
                return(KICK);
            }
        }
        return(GetBallDirection());
    }


    public void assignAttacker() {
        int attacker = 0;
        for (int i = 0; i < 4; i++) {
            if (distancesToBall[i] < distancesToBall[attacker]) {
                attacker = i;
            }
            if (playersOnBall[i] == 1) {
                assignOthers(i);
                break;
            }
        }
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
        for (int i = 0; i < 4; i++) {
            positions[i] = 0;
        }

        positions[attacker] = ATTACK;

        //assigning leftForward
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                if (playersY[i] < lowY) {
                    lowY = playersY[i];
                    newLeftForward = i;
                }
            }
        }
        positions[newLeftForward] = LEFTFORWARD;

        //assigning rightForward
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                if (playersY[i] > highY) {
                    highY = playersY[i];
                    newRightForward = i;
                }
            }
        }
        positions[newRightForward] = RIGHTFORWARD;

        //assigning defender
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                positions[i] = DEFENDER;
                break;
            }
        }
    }

    public void InitializeGame () {
        playersX = new int[4];
        playersY = new int[4];
        distancesToBall = new int[4];
        someoneOnBall = false;
        playersOnBall = new int[4];
        positions = new int[4];
        positions[0] = ATTACK;
        positions[1] = RIGHTFORWARD;
        positions[2] = LEFTFORWARD;
        positions[3] = DEFENDER;
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
        int direction = PLAYER;
        for (int i = 0; i <= 7; i++) {
            if (Look(i) == BALL) {
                direction = i;
            }
        }
        switch (direction) {
            case NORTH:
                move = NORTHEAST;
            case NORTHEAST:
                move = EAST;
            case EAST:
                move = NORTHEAST;
            case SOUTHEAST:
                move = EAST;
            case SOUTH:
                move = SOUTHEAST;
            case SOUTHWEST:
                move = SOUTH;
            case WEST:
                move = KICK;
            case NORTHWEST:
                move = NORTH;
        }
        /* If there is an opponent blocking the move you want to make */
        if (Look(move) != EMPTY || Look(move) == BOUNDARY) {
            move = AlternateMove(move);
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
            case LEFTFORWARD: action =  leftForward();
                break;
            case RIGHTFORWARD: action =  rightForward();
                break;
            case DEFENDER: action =  rightForward();
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
        switch (positions[1]) {
            case ATTACK: action =  Attack();
                break;
            case LEFTFORWARD: action =  leftForward();
                break;
            case RIGHTFORWARD: action =  rightForward();
                break;
            case DEFENDER: action =  rightForward();
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
        switch (positions[2]) {
            case ATTACK: action =  Attack();
                break;
            case LEFTFORWARD: action =  leftForward();
                break;
            case RIGHTFORWARD: action =  rightForward();
                break;
            case DEFENDER: action =  rightForward();
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
            case LEFTFORWARD: action =  leftForward();
                break;
            case RIGHTFORWARD: action =  rightForward();
                break;
            case DEFENDER: action =  rightForward();
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
