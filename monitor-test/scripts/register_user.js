import http from 'k6/http';
import { check, sleep } from "k6";
import { uuidv4 } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

const isNumeric = (value) => /^\d+$/.test(value);

const default_vus = 500;

const target_vus_env = `${__ENV.TARGET_VUS}`;
const target_vus = isNumeric(target_vus_env) ? Number(target_vus_env) : default_vus;

/*export const options = {
  scenarios: {
    constant_request_rate: {
      executor: 'constant-arrival-rate',
      rate: 10000,
      timeUnit: '1s', // 1000 iterations per second, i.e. 1000 RPS
      duration: '120s',
      preAllocatedVUs: 100, // how large the initial pool of VUs would be
      maxVUs: 200, // if the preAllocatedVUs are not enough, we can initialize more
    },
  },
};*/

export let options = {
  stages: [
      // Ramp-up from 1 to TARGET_VUS virtual users (VUs)
      { duration: "2m", target: target_vus },

      // Stay at rest on TARGET_VUS VUs
      { duration: "1m", target: target_vus },

      // Ramp-down from TARGET_VUS to 0 VUs
      { duration: "30s", target: 0 }
  ]
};

export default function () {
  const rnd = uuidv4();
    const payload = JSON.stringify({
     id: "u "+rnd,
      name: "User "+rnd
    });

    const params = {
      headers: {
        'Content-Type': 'application/json',
      },
    };

    let reg_res = http.post('http://app:8080/user', payload, params);
    // console.log(reg_res)
    check(reg_res, {
        'status is 201': (r) => r.status === 201,
      });
}