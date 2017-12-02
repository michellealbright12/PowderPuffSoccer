import java.awt.*;

public class PowderPuff extends Player {
    //really great comment
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
    
    //leftForward function
    
    //rightForward function
    
    //defender function
    
    //assignAttacker function
    public void 
    
    //assignOthers function
    
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
    }
    
    public int GetPlayerClosestToBall() {
        int minDistance = distancesToBall[0];
        int closestPlayer = 0;
        for (int i = 1; i <= 3; i++) {
            if (distancesToBall[i] < minDistance) {
                minDistance = distancesToBall[i];
                closestPlayer = i;
                if (distancesToBall[i] == 1) {
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
        int direction = GetBallDirection();
        
        switch (direction) {
            case NORTH:
                move = NORTHEAST;
            case NORTHEAST:
                move = NORTH;
            case EAST:
                move = KICK;
            case SOUTHEAST:
                move = SOUTH;
            case SOUTH:
                move = SOUTHWEST;
            case SOUTHWEST:
                move = WEST;
            case WEST:
                move = NORTHWEST;
            case NORTHWEST:
                move = WEST;
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
            return GetBallDirection();
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
            return GetBallDirection();
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
            return GetBallDirection();
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
            return GetBallDirection();
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
