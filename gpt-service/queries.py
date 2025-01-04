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
             "content": get_topic_content()}
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
             "content": get_guess_word_content(guessed_word)},
            {"role": "user",
             "content": question}
        ]
    )
    return completion.choices[0].message.content


def get_guess_word_content(guessed_word):
    content = f"We play guessing game. The given word is {guessed_word}. I send you a question and you "
    f"should answer only the one word with nothing else. If my question is related to guessed "
    f"word, answer something in agreement. If my question is not related to guessed word answer "
    f"something denial. If I guessed the"
    f"word then answer 'done' and only in this format, for example, if i wrote question with this "
    f"word or synonym. If you"
    f"dont know answer that you don't know"
    return content


def get_topic_content():
    content = f"I playing guessing game, you should guess word related to topic you choose. Send only the "
    f"topic and the word formating like topic:word and nothing else. Send every time new topic, "
    f"you yet suggested me topics {topics_used}"
    return content
