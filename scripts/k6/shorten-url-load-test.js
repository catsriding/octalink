import http from 'k6/http';
import { check } from 'k6';

export const options = {
  scenarios: {
    constant_rps: {
      executor: 'constant-arrival-rate',
      rate: 100,
      timeUnit: '1s',
      duration: '10s',
      preAllocatedVUs: 100,
      maxVUs: 200,
    },
  },
};

function generateRandomString(length) {
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_';
  let result = '';
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * characters.length));
  }
  return result;
}

function generateRandomUrl() {
  const base = 'https://www.catsriding.com/';
  const randomLength = Math.floor(Math.random() * (2048 - base.length)) + 1;
  const randomPath = generateRandomString(randomLength);
  return `${base}${randomPath}`;
}

export default function () {
  const url = 'http://localhost:8080/shorten';
  const payload = JSON.stringify({
    url: generateRandomUrl(),
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}