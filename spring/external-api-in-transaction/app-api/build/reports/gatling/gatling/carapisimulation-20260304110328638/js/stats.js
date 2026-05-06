var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "50",
        "ok": "20",
        "ko": "30"
    },
    "minResponseTime": {
        "total": "50338",
        "ok": "50338",
        "ko": "60000"
    },
    "maxResponseTime": {
        "total": "60004",
        "ok": "50377",
        "ko": "60004"
    },
    "meanResponseTime": {
        "total": "56144",
        "ok": "50359",
        "ko": "60001"
    },
    "standardDeviation": {
        "total": "4724",
        "ok": "12",
        "ko": "1"
    },
    "percentiles1": {
        "total": "60001",
        "ok": "50355",
        "ko": "60001"
    },
    "percentiles2": {
        "total": "60001",
        "ok": "50368",
        "ko": "60001"
    },
    "percentiles3": {
        "total": "60003",
        "ok": "50377",
        "ko": "60004"
    },
    "percentiles4": {
        "total": "60004",
        "ok": "50377",
        "ko": "60004"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0.0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 20,
    "percentage": 40.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 30,
    "percentage": 60.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.82",
        "ok": "0.33",
        "ko": "0.49"
    }
},
contents: {
"req_test-3556498": {
        type: "REQUEST",
        name: "test",
path: "test",
pathFormatted: "req_test-3556498",
stats: {
    "name": "test",
    "numberOfRequests": {
        "total": "50",
        "ok": "20",
        "ko": "30"
    },
    "minResponseTime": {
        "total": "50338",
        "ok": "50338",
        "ko": "60000"
    },
    "maxResponseTime": {
        "total": "60004",
        "ok": "50377",
        "ko": "60004"
    },
    "meanResponseTime": {
        "total": "56144",
        "ok": "50359",
        "ko": "60001"
    },
    "standardDeviation": {
        "total": "4724",
        "ok": "12",
        "ko": "1"
    },
    "percentiles1": {
        "total": "60001",
        "ok": "50355",
        "ko": "60001"
    },
    "percentiles2": {
        "total": "60001",
        "ok": "50368",
        "ko": "60001"
    },
    "percentiles3": {
        "total": "60003",
        "ok": "50377",
        "ko": "60004"
    },
    "percentiles4": {
        "total": "60004",
        "ok": "50377",
        "ko": "60004"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0.0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 20,
    "percentage": 40.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 30,
    "percentage": 60.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.82",
        "ok": "0.33",
        "ko": "0.49"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
