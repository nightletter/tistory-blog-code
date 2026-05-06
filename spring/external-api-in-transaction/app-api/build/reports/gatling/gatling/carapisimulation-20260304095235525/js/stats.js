var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "50",
        "ok": "5",
        "ko": "45"
    },
    "minResponseTime": {
        "total": "30144",
        "ok": "30144",
        "ko": "60000"
    },
    "maxResponseTime": {
        "total": "60010",
        "ok": "30170",
        "ko": "60010"
    },
    "meanResponseTime": {
        "total": "57018",
        "ok": "30156",
        "ko": "60003"
    },
    "standardDeviation": {
        "total": "8954",
        "ok": "8",
        "ko": "3"
    },
    "percentiles1": {
        "total": "60001",
        "ok": "30155",
        "ko": "60002"
    },
    "percentiles2": {
        "total": "60002",
        "ok": "30157",
        "ko": "60002"
    },
    "percentiles3": {
        "total": "60010",
        "ok": "30170",
        "ko": "60010"
    },
    "percentiles4": {
        "total": "60010",
        "ok": "30170",
        "ko": "60010"
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
    "count": 5,
    "percentage": 10.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 45,
    "percentage": 90.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.82",
        "ok": "0.08",
        "ko": "0.74"
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
        "ok": "5",
        "ko": "45"
    },
    "minResponseTime": {
        "total": "30144",
        "ok": "30144",
        "ko": "60000"
    },
    "maxResponseTime": {
        "total": "60010",
        "ok": "30170",
        "ko": "60010"
    },
    "meanResponseTime": {
        "total": "57018",
        "ok": "30156",
        "ko": "60003"
    },
    "standardDeviation": {
        "total": "8954",
        "ok": "8",
        "ko": "3"
    },
    "percentiles1": {
        "total": "60001",
        "ok": "30155",
        "ko": "60002"
    },
    "percentiles2": {
        "total": "60002",
        "ok": "30157",
        "ko": "60002"
    },
    "percentiles3": {
        "total": "60010",
        "ok": "30170",
        "ko": "60010"
    },
    "percentiles4": {
        "total": "60010",
        "ok": "30170",
        "ko": "60010"
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
    "count": 5,
    "percentage": 10.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 45,
    "percentage": 90.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.82",
        "ok": "0.08",
        "ko": "0.74"
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
