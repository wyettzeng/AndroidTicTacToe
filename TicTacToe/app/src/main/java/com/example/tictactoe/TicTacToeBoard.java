package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    public String p1, p2;

    private final int boardColor, XColor, OColor, WinningColor;
    public TextView label;

    boolean pvp;

    private final Paint paint = new Paint();
    int cellSize;

    TicTacToeLogic board = new TicTacToeLogic();
    int turn = 1;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            WinningColor = a.getInteger(R.styleable.TicTacToeBoard_winningColor, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);
        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight());

        cellSize = dimensions / 3;
        setMeasuredDimension(dimensions, dimensions);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        refresh(canvas);

        String out;
        if(board.win() == 0){
            if (pvp) {
                if (turn == 1) {
                    out = "It's " + p1 + "'s turn to play!";
                } else {
                    out = "It's " + p2 + "'s turn to play!";
                }
            } else {
                out = "";
            }
        } else if (board.win() == 1) {
            out = p1 + " has Won!";
        } else if (board.win() == 2){
            out = p2 + " has Won!";
        } else {
            out = "It's a draw!";
        }
        label.setText(out);
    }

    private void refresh(Canvas canvas){
        int[][] b = board.getGameBoard();
        for(int row = 0; row < 3; row ++) {
            for (int col = 0; col < 3; col++) {
                if (b[row][col] == 1) {
                    drawX(canvas, row, col);
                } else if (b[row][col] == 2) {
                    drawO(canvas, row, col);
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y / cellSize) - 1;
            int col = (int) Math.ceil(x / cellSize) - 1;

            boolean success = board.update(col, row, turn);
            if(success && turn == 1) {
                turn = 2;
                invalidate();
            } else if(success){
                turn = 1;
                invalidate();
            }
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for (int i = 1; i < 3; ++i){
            canvas.drawLine(cellSize * i, 0, cellSize * i, canvas.getWidth(), paint);
            canvas.drawLine(0, cellSize * i, canvas.getWidth(), cellSize * i, paint);
        }
    }

    private void drawX(Canvas canvas, int row, int column){
        paint.setColor(XColor);
        double offset = 0.1;

        canvas.drawLine((float) (column * cellSize + cellSize * offset),
                (float) (row * cellSize + cellSize * offset),
                (float) ((column + 1) * cellSize - cellSize * offset),
                (float) ((row + 1) * cellSize - cellSize * offset),
                paint);

        canvas.drawLine((float) ((column + 1) * cellSize - cellSize * offset),
                (float) (row * cellSize + cellSize * offset),
                (float) (column * cellSize + cellSize * offset),
                (float) ((row + 1) * cellSize - cellSize * offset),
                paint);
    }

    private void drawO(Canvas canvas, int row, int column){
        paint.setColor(OColor);
        double offset = 0.1;
        canvas.drawOval((float) (column * cellSize + cellSize * offset),
                (float) (row * cellSize + cellSize * offset),
                (float) ((column + 1) * cellSize - cellSize * offset),
                (float) ((row + 1) * cellSize - cellSize * offset),
                paint);
    }


    public void resetGame() {
        board.reset();
        turn = 1;
        invalidate();
    }
}
