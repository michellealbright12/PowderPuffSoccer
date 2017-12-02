import java.awt.*;

public class PowderPuff extends Player {
    
    public PowderPuff()
    {
        super();
        CalledSuper = true;
        Run = true;
    }
    static int playersX[];
    static int playersY[];
    static int distancesToBall[];
    static boolean someoneOnBall;
    static int playersOnBall[];
    static final int attack = 1;
    static final int leftForward = 2;
    static final int rightForward = 3;
    static final int defender = 4;
    static int positions[];
    
    
    //attack function
    public int Attack() {
        int move = PLAYER;
        move = moveAroundBall();
        return move;
    }
    
    //leftForward function
    public int LeftForward() {
        
    }
    
    //rightForward function
    
    //defender function
    
    //assignAttacker function
    
    //assignOthers function
    
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
        while (Look(move) != EMPTY) {
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
                if (distancesToBall[i] == 0) {
                    someoneOnBall = true;
                    playersOnBall[i] = 1;
                }
            }
        }
        return closestPlayer;
    }
    
    public int GetLongestUnobstructedPass(int passer) {
        int receiver = 0;
        
        return receiver;
    }
    
    public int moveAroundBall() {
        int move = PLAYER;
        int direction = PLAYER;
        for (int i = 0; i <= 7; i++) {
            if (Look(i) == BALL) {
                direction = i;
            }
        }
        switch (direction) {
            case NORTH:
                move = WEST;
            case NORTHEAST:
                move = NORTH;
            case EAST:
                move = KICK;
            case SOUTHEAST:
                move = SOUTH;
            case SOUTH:
                move = WEST;
            case SOUTHWEST:
                move = WEST;
            case WEST:
                move = NORTH;
            case NORTHWEST:
                move = WEST;
        }
        /* If there is an opponent blocking the move you want to make */
        if (Look(move) != EMPTY) {
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
                return moveAroundBall();
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
                return moveAroundBall();
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
                return moveAroundBall();
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
                return moveAroundBall();
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
        //return NORTH;
    }
    
}
