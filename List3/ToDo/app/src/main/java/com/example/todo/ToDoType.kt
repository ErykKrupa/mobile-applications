package com.example.todo

enum class ToDoType {
    Work {
        override fun getWeight(): Int {
            return 1
        }
    },
    Study {
        override fun getWeight(): Int {
            return 2
        }
    },
    Home {
        override fun getWeight(): Int {
            return 3
        }
    },
    You {
        override fun getWeight(): Int {
            return 4
        }
    },
    Administration {
        override fun getWeight(): Int {
            return 5
        }
    };
    internal abstract fun getWeight(): Int
}