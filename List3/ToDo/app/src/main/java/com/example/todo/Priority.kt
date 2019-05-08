package com.example.todo

enum class Priority {
    Inconsequential {
        override fun getWeight(): Int {
            return 1
        }
    },
    Unimportant {
        override fun getWeight(): Int {
            return 2
        }
    },
    Significant {
        override fun getWeight(): Int {
            return 3
        }
    },
    Important {
        override fun getWeight(): Int {
            return 4
        }
    },
    Crucial {
        override fun getWeight(): Int {
            return 5
        }
    };

    internal abstract fun getWeight(): Int
}
