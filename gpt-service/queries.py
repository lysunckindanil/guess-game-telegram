from openai import OpenAI

with open("apikey") as f:
    apikey = f.read()
client = OpenAI(
    api_key=apikey
)

topics_used = []


def choose_topic():
    completion = client.chat.completions.create(
        model="gpt-4o-mini",
        store=False,
        messages=[
            {"role": "user",
             "content": f"I playing guessing game, you should guess word related to topic you choose. Send only the topic and the word formating like topic:word and nothing else. Send every time new topic, you yet suggested me topics {topics_used}"}
        ]
    )
    result = completion.choices[0].message.content.split(":")
    topics_used.append(result[0])
    return result


def guess_word(guessed_word, question):
    completion = client.chat.completions.create(
        model="gpt-4o-mini",
        store=False,
        messages=[
            {"role": "system",
             "content": f"We play guessing game. The given word is {guessed_word}. I send you a question and you should answer only the one word with nothing else. If my question is related to guessed word, answer yes. If my question is not related to guessed word answer no. If I guessed the word then answer done, for example, if i wrote question with this word or synonym. If you dont know answer skip"},
            {"role": "user",
             "content": question}
        ]
    )
    return completion.choices[0].message.content
