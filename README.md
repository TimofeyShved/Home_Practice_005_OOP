# Основы 

2.1. Генерация случайного элемента с весом

Задача
--------

Напишите класс, конструктор которого принимает два массива: массив значений и массив весов значений.
Класс должен содержать метод, который будет возвращать элемент из первого массива случайным образом, с учётом его веса.

##Пример:

Дан массив [1, 2, 3], и массив весов [1, 2, 10].

В среднем, значение «1» должно возвращаться в 2 раза реже, чем значение «2» и в десять раз реже, чем значение «3».


Решение:
--------
    /*
    Решение основывается на геометрической идее:
    Будем считать, что веса — это длины некоторых отрезков.
    Тогда надо "уложить" все отрезки в один общий,
    генерировать случайное значение из этого общего отрезка,
    определять в какой из наших отрезков попало значение:
    |-|--|----------|
    0-1--3----------13
              ^
    */

    class RandomFromArray {
    private int[] values; // значения
    private int[] weights; // веса
    private int[] ranges; // левые границы отрезков
    private int sum; // общая длина всех отрезков
    
        public RandomFromArray(int[] values, int[] weights) {
            this.values = values;
            this.weights = weights;
            ranges = new int[values.length];
    
            // Сумма длин всех отрезков
            sum = 0;
            for (int weight : weights) {
                sum += weight;
            }
    
            // Заполняем ranges, левыми границами
            int lastSum = 0;
            for (int i = 0; i < ranges.length; i++) {
                ranges[i] = lastSum;
                lastSum += weights[i];
            }
        }
    
        /*
            Массив ranges уже заполнен, так что остаётся
            сгенерировать значение в промежутке [0;sum],
            и найти отрезок, содержащий это значение:
         */
        public int getRandom() {
            int random = (int) (Math.random() * (sum - 1));
    
            int ourRangeIndex = 0;
            for (int i = 0; i < ranges.length; i++) {
                if (ranges[i] > random) {
                    break;
                }
                ourRangeIndex = i;
            }
    
            return values[ourRangeIndex];
        }
    }

Но, так как массив ranges отсортирован, то можно (и нужно) использовать бинарный поиск:

    public int getRandom() {
    int random = (int) (Math.random() * (sum - 1));
    
            int index = Arrays.binarySearch(ranges, random);
            return values[index >= 0 ? index : -index - 2];
        }

Есть ещё один вариант решения этой задачи. Можно создать массив, размер которого равен сумме всех весов. Тогда выбор случайного элемента сводится к генерации случайного индекса. То есть, если дан массив значений [1, 2, 3], и массив весов [1, 2, 10], то можно сразу создать массив [1, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3] и извлекать из него случайный элемент:

    class RandomFromArray {
    private int[] extended_values; // значения
    
        public RandomFromArray(int[] values, int[] weights) {
            // Сумма длин всех отрезков
            int sum = 0;
            for (int weight : weights) {
                sum += weight;
            }
    
            extended_values = new int[sum];
            int cursor = 0;
    
            for(int i = 0; i < weights.length; i++){
                for(int j = 0; j < weights[i]; j++){
                    extended_values[cursor++] = values[i];
                }
            }
        }
    
        /*
            Массив extended_values уже заполнен,
            так что остаётся сгенерировать значение в промежутке
            [0; extended_values.length)
         */
        public int getRandom() {
            int random = (int) (Math.random() * ( extended_values.length - 1));
            return extended_values[random];
        }
    }

У этого решения есть преимущество — время извлечения случайного элемента O(1), в отличии от log(n) в предыдущем решении. Однако требует много памяти: