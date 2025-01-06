FROM python:latest

WORKDIR /gpt-service

RUN pip install --no-cache-dir --upgrade pip \
  && pip install --no-cache-dir -r requirements.txt

COPY . .

CMD ["python", "app.py"]