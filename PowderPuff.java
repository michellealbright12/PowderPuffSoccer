/*
- Fix isClearPath function to determine which direction to look in and check to
  see if that is clear : CLAIRE
- Check to make sure move around ball works correctly : MICHELLE
- Assign players to positions : MICHELLE
- Get other people to move : CLAIRE
- Finish leftForward and rightForward functions : CLAIRE
- Defender function : MICHELLE
*/


import java.awt.*;

public class PowderPuff extends Player {

    public PowderPuff() {
        super();
        CalledSuper = true;
        Run = true;
    }

    static int playersX[];
    static int playersY[];
    static int distancesToBall[];
    static boolean someoneOnBall;
    static int playersOnBall[];
    static final int ATTACK = 1;
    static final int LEFTFORWARD = 2;
    static final int RIGHTFORWARD = 3;
    static final int DEFENDER = 4;
    static int positions[];

    /* Check to see if there is a "clear" path by checking the direction around
       each player */
    public boolean isClearPath(int x1, int y1, int x2, int y2) {
      int xDifference = abs(x1 - x2);
      int yDifference = abs(y1 - y2);
      int startX, endX;
      int startY, endY;

      if (y1 < y2) {
        // check to the right of y1
        // and the left of y2
        startY = y1;
        endY = y2;
      } else {
        // check to the right of x2
        // and the left of x1
        startY = y2;
        endY = y1;
      }
      if (x1 < x2) {
        startX = x1;
        endX = x2;
        // check to the right of x1
        // and the left of x2
      } else {
        startX = x2;
        endX = x1;
        // check to the right of x2
        // and the left of x1
      }

      // check straight line in x direction
      for (int i = startX; i < endX; i++) {
          // if box (i, startY) is ! EMPTY, return false
      }

      // check straight line in y Direction
      for (int j = startY; j < endY; j++) {

      }

      // check straight line in NE Direction

      // check straight line in NW direction

      return true;
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
      } else if isClearPath(attackX, attackY, secondX + 1, secondY)) {
        return EAST;
      } else if isClearPath(attackX, attackY, secondX - 1, secondY)) {
        return WEST;
      } else if isClearPath(attackX, attackY, secondX, secondY - 1)) {
        return NORTH;
      } else if isClearPath(attackX, attackY, secondX, secondY + 1)) {
        return SOUTH;
      } else if isClearPath(attackX, attackY, secondX + 1, secondY + 1)) {
        return SOUTHEAST;
      } else if isClearPath(attackX, attackY, secondX - 1, secondY + 1)) {
        return SOUTHWEST;
      } else if isClearPath(attackX, attackY, secondX + 1, secondY - 1)) {
        return NORTHEAST;
      } else if isClearPath(attackX, attackY, secondX - 1, secondY - 1)) {
        return NORTHWEST;
      } else {
        return GetTeammateDirection();
      }
    }

    public int KickToClearPath() {
        int move = PLAYER;
        int attackX, attackY;
        int forwardX, forwardY;
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
    public int Attack() {
        int move = PLAYER;
        if (KickToClearPath() != PLAYER) {
            return KickToClearPath();
        }
        move = MoveAroundBall();
        return move;
    }

    //leftForward function
    public int LeftForward() {
        int move = WEST;
        //find attack index
        int attack = 0;
        //find leftforward index
        int leftForward = 0;
        for (int i = 0; i <= 3; i++) {
          if (positions[i] == ATTACK) {
            attack = i;
          }
          if (positions[i] == LEFTFORWARD) {
            leftForward = i;
          }
        }
        if (isClearPath(playersX[attack], playersY[attack],
            playersX[leftForward], playersY[leftForward])) {
            move = MoveToClearPath();
        }
        return move;
    }

    //rightForward function
    public int rightForward() {
      int move = WEST;
      //find attack index
      int attack = 0;
      //find rightForward index
      int rightForward = 0;
      for (int i = 0; i <= 3; i++) {
        if (positions[i] == ATTACK) {
          attack = i;
        }
        if (positions[i] == RIGHTFORWARD) {
          rightForward = i;
        }
      }
      if (isClearPath(playersX[attack], playersY[attack],
          playersX[rightForward], playersY[rightForward])) {
          move = MoveToClearPath();
      }
      return move;
  }
    //defender function

    //assignAttacker function

    //assignOthers function


    public void assignAttacker() {
        int attacker = 0;
        for (int i = 0; i < 4; i++) {
            if (distancesToBall[i] < distancesToBall[attacker]) {
                attacker = i;
            }
            if (playersOn[i] == 1) {
                assignOthers(i);
                break;
            }
        }
        assignOthers(attacker);
    }

    //assignOthers function
    public void assignOthers(int attacker) {
        int lowX = INTEGER.MAX_VALUE;
        int highX = INTEGER.MIN_VALUE;
        int lowY = INTEGER.MAX_VALUE;
        int highY = INTEGER.MIN_VALUE;

        for (int i = 0; i < 4; i++) {
            positions[i] == 0;
        }

        positions[attacker] = ATTACK;

        //assigning leftForward
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                if (playersY[i] < lowY) {
                    lowY = players[i];
                    newLeftForward = i;
                }
            }
        }
        positions[newLeftForward] = LEFTFORWARD;

        //assigning rightForward
        for (int i = 0; i < 4; i++) {
            if (positions[i] == 0) {
                if (playersY[i] > highY) {
                    highY = players[i];
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
    }

    public void InitializePoint() {
        for (int i = 0; i <= 3; i++) {
            playersX[i] = 0;
            playersY[i] = 0;
            playersOnBall[i] = 0;
        }
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
                move = EAST;
            case NORTHEAST:
                move = NORTH;
            case EAST:
                move = NORTH;
            case SOUTHEAST:
                move = EAST;
            case SOUTH:
                move = EAST;
            case SOUTHWEST:
                move = SOUTH;
            case WEST:
                move = KICK;
            case NORTHWEST:
                move = EAST;
        }
        /* If there is an opponent blocking the move you want to make */
        if (Look(move) != EMPTY || Look(move) == BOUNDARY) {
            move = AlternateMove(move);
        }
        return move;
    }

    public int Player1() {
        int action = PLAYER;

        playersX[0] = GetLocation().x;
        playersY[0] = GetLocation().y;

        distancesToBall[0] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();

        if (closestPlayer == 0) {
            if (playersOnBall[0] == 1) {
                return MoveAroundBall();
            }
            if (Look(GetBallDirection()) == OPPONENT) {
                return AlternateMove(GetBallDirection());
            } else {
                return GetBallDirection();
            }
        }
        return action;
    }

    public int Player2() {
        int action = PLAYER;

        playersX[1] = GetLocation().x;
        playersY[1] = GetLocation().y;

        distancesToBall[1] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();
        if (closestPlayer == 1) {
            if (playersOnBall[1] == 1) {
                return MoveAroundBall();
            }
            if (Look(GetBallDirection()) == OPPONENT) {
                return AlternateMove(GetBallDirection());
            } else {
                return GetBallDirection();
            }
        }
        return action;
    }

    public int Player3() {
        int action = PLAYER;

        playersX[2] = GetLocation().x;
        playersY[2] = GetLocation().y;

        distancesToBall[2] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();
        if (closestPlayer == 2) {
            if (playersOnBall[2] == 1) {
                return MoveAroundBall();
            }
            if (Look(GetBallDirection()) == OPPONENT) {
                return AlternateMove(GetBallDirection());
            } else {
                return GetBallDirection();
            }
        }
        return action;
    }

    public int Player4() {
        int action = PLAYER;

        playersX[3] = GetLocation().x;
        playersY[3] = GetLocation().y;

        distancesToBall[3] = GetBallDistance();
        int closestPlayer = GetPlayerClosestToBall();
        if (closestPlayer == 3) {
            if (playersOnBall[3] == 1) {
                return MoveAroundBall();
            }
            if (Look(GetBallDirection()) == OPPONENT) {
                return AlternateMove(GetBallDirection());
            } else {
                return GetBallDirection();
            }
        }
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

    /** Think about it. */
    public final int GetTeammateDirection(int n)
    {
        return Parent.GetTeammateDirection(this, n);
    }

}
