package com.example.zmeyka

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun main() {
        val game = SnakeGame()
        game.start()
    }

    class SnakeGame {
        private val width = 20
        private val height = 20
        private val random = Random()
        private val grid = Array(width) { Array(height) { false } }
        private val snake = LinkedList<Point>()
        private var food = Point(0, 0)

        private fun updateFood() {
            while (grid[food.x][food.y]) {
                food = Point(random.nextInt(width), random.nextInt(height))
            }
        }

        private fun moveSnake() {
            val head = snake.peekFirst()
            val newX = head.x + random.nextInt(3) - 1
            val newY = head.y + random.nextInt(3) - 1

            if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
                return
            }

            val tail = snake.pollLast()
            grid[tail.x][tail.y] = false

            val newHead = Point(newX, newY)
            snake.offerFirst(newHead)
            grid[newHead.x][newHead.y] = true

            if (newHead == food) {
                updateFood()
                snake.offerLast(tail)
                grid[tail.x][tail.y] = true
            }
        }

        private fun print() {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    print(if (grid[x][y] || snake.contains(Point(x, y))) "O" else ".")
                }
                println()
            }
            println()
        }

        fun start() {
            snake.offerLast(Point(width / 2, height / 2))
            grid[width / 2][height / 2] = true
            updateFood()

            while (true) {
                moveSnake()
                print()
                Thread.sleep(500)
            }
        }
    }

}